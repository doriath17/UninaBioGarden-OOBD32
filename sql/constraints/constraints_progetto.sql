-- VINCOLI PROGETTO

DROP TRIGGER IF EXISTS insert_progetto ON progetto;
DROP FUNCTION IF EXISTS check_insert_progetto() CASCADE;
DROP TRIGGER IF EXISTS update_progetto ON progetto;
DROP FUNCTION IF EXISTS check_update_progetto() CASCADE;
-- ============================================================
-- INSERT 
-- ============================================================

CREATE OR REPLACE FUNCTION check_insert_progetto()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.data_fine IS NOT NULL THEN
    RAISE EXCEPTION 'Un progetto appena creato deve avere ''data_fine = NULL''';
  END IF;

  IF NEW.stato IS NOT NULL AND NEW.stato <> 'ATTIVO' THEN
    RAISE EXCEPTION 'Un nuovo progetto deve avere ''stato = ATTIVO''';
  END IF;

  IF NOT is_proprietario(NEW.id_proprietario) THEN
    RAISE EXCEPTION 'L''utente con ID = % non è un proprietario', NEW.id_proprietario;
  END IF;

  IF NOT EXISTS (
    SELECT 1
    FROM lotto l
    WHERE l.id = NEW.id_lotto
    AND l.id_proprietario = NEW.id_proprietario
  ) THEN
    RAISE EXCEPTION 'Il lotto % non appartiene all''utente %', NEW.id_lotto, NEW.id_proprietario;
  END IF;

  IF EXISTS (
    SELECT 1
    FROM progetto p
    WHERE p.id_lotto = NEW.id_lotto
    AND p.stato = 'ATTIVO'
  ) THEN
    RAISE EXCEPTION 'Il lotto % è già occupato da un progetto attivo', NEW.id_lotto;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_progetto
BEFORE INSERT ON progetto
FOR EACH ROW
EXECUTE FUNCTION check_insert_progetto();

-- ============================================================
-- UPDATE -- modalità read only
-- ============================================================

CREATE OR REPLACE FUNCTION check_update_progetto()
RETURNS TRIGGER AS $$
BEGIN
  IF OLD.stato = 'CONCLUSO' THEN 
    RAISE EXCEPTION 'Impossibile modificare un progetto concluso';
  END IF;

  IF NEW.id_proprietario <> OLD.id_proprietario THEN 
    RAISE EXCEPTION 'Impossibile modificare il proprietario del progetto';
  END IF;

  IF NEW.id_lotto <> OLD.id_lotto THEN 
    RAISE EXCEPTION 'Impossibile modificare il lotto del progetto';
  END IF;

  IF NEW.stato = 'CONCLUSO' AND NEW.data_fine IS NULL THEN 
    NEW.data_fine := CURRENT_TIMESTAMP;
  END IF;

  IF NEW.stato = 'CONCLUSO' AND OLD.stato <> 'CONCLUSO' THEN 
    IF EXISTS (
      SELECT 1
      FROM coltivazione c
      JOIN progetto p ON p.id = c.id_progetto
      WHERE p.id = NEW.id
      AND c.stato <> 'CONCLUSA'
    ) THEN
      RAISE EXCEPTION 'Non è possibile concludere un progetto con coltivazioni non concluse';
    END IF;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;  

CREATE TRIGGER update_progetto
BEFORE UPDATE ON progetto
FOR EACH ROW
EXECUTE FUNCTION check_update_progetto();