-- VINCOLI ATTIVITA

DROP TRIGGER IF EXISTS insert_attivita ON attivita;
DROP FUNCTION IF EXISTS check_insert_attivita() CASCADE;
DROP TRIGGER IF EXISTS updates_attivita ON attivita;
DROP FUNCTION IF EXISTS check_update_attivita() CASCADE;
DROP TRIGGER IF EXISTS updates_attivita_raccolta ON attivita;
DROP FUNCTION IF EXISTS check_update_attivita_raccolta() CASCADE;

-- ============================================================
-- INSERT di una attività
-- ============================================================


CREATE OR REPLACE FUNCTION check_insert_attivita()
RETURNS TRIGGER AS $$
DECLARE 
  v_stato_coltivazione coltivazione.stato%TYPE;
  v_coltivatore utente%ROWTYPE;
BEGIN
  IF NEW.stato <> 'PIANIFICATA' THEN 
    RAISE EXCEPTION 'Un attività appena creata deve avere stato ''PIANIFICATA''';
  END IF;

  IF NEW.data_fine IS NOT NULL THEN 
    RAISE EXCEPTION 'Un attività appena creata deve avere ''data_fine = NULL''';
  END IF;

  -- blocco sulle coltivazioni in stato terminale
  SELECT stato INTO v_stato_coltivazione 
  FROM coltivazione
  WHERE id = NEW.id_coltivazione;

  IF v_stato_coltivazione IN ('CONCLUSA', 'IN_RACCOLTA') THEN
    RAISE EXCEPTION 'Impossibile inserire una nuova attività ad una coltivazione conclusa o in raccolta';
  END IF;

  -- check sul coltivatore
  SELECT * INTO v_coltivatore
  FROM utente
  WHERE id = NEW.id_coltivatore;

  IF v_coltivatore.tipo <> 'COLTIVATORE' THEN 
    RAISE EXCEPTION 'L''utente con ID = % non è un coltivatore', NEW.id_coltivatore;
  END IF;

  IF NOT EXISTS (
    SELECT 1
    FROM lavora_per lp
    JOIN coltivazione c ON c.id_progetto = lp.id_progetto
    WHERE lp.id_coltivatore = NEW.id_coltivatore
    AND c.id = NEW.id_coltivazione
  ) THEN
    RAISE EXCEPTION 'Il coltivatore con ID = % non è assegnato al progetto della coltivazione %', NEW.id_coltivatore, NEW.id_coltivazione;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_attivita
BEFORE INSERT ON attivita
FOR EACH ROW
EXECUTE FUNCTION check_insert_attivita();


-- ============================================================
-- UPDATE attributi immutabili
-- ============================================================


CREATE FUNCTION check_update_attivita() 
RETURNS TRIGGER AS $$ 
BEGIN 
  IF OLD.stato = 'COMPLETATA' THEN 
    RAISE EXCEPTION 'Non è possibile modificare un''attività terminata';
  END IF;

  IF NEW.data_pianificazione <> OLD.data_pianificazione THEN 
    RAISE EXCEPTION '''data_pianificazione'' non può essere modificata dopo la creazine dell''attività';
  END IF;

  IF NEW.id_coltivazione <> OLD.id_coltivazione THEN 
    RAISE EXCEPTION 'Non è possibile modificare a quale coltivazione un''attività è associata';
  END IF;

  IF OLD.stato = 'PIANIFICATA' AND NEW.stato = 'IN_CORSO' AND NEW.data_inizio IS NULL THEN 
    NEW.data_inizio := CURRENT_TIMESTAMP;
  END IF;

  IF NEW.stato = 'PIANIFICATA' AND OLD.stato = 'IN_CORSO' THEN 
    RAISE EXCEPTION 'Non è possibile riportare un''attività in stato ''PIANIFICATA''';
  END IF;

  IF NEW.stato = 'COMPLETATA' AND NEW.data_fine IS NULL THEN 
    NEW.data_fine := CURRENT_TIMESTAMP;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER updates_attivita
BEFORE UPDATE ON attivita
FOR EACH ROW
EXECUTE FUNCTION check_update_attivita();

-- ============================================================
-- UPDATE -- raccolta
-- ============================================================

CREATE FUNCTION check_update_attivita_raccolta()
RETURNS TRIGGER AS $$
DECLARE 
  v_id_coltivazione coltivazione.id%TYPE;
BEGIN

  SELECT id_coltivazione INTO v_id_coltivazione
  FROM attivita
  WHERE id = NEW.id;

  IF NEW.stato = 'IN_CORSO' AND OLD.stato = 'PIANIFICATA' THEN
    IF EXISTS (
      SELECT 1 
      FROM attivita 
      WHERE stato <> 'COMPLETATA' 
        AND id_coltivazione = v_id_coltivazione 
        AND id <> OLD.id
    ) THEN 
      RAISE EXCEPTION 'Non è possibile iniziare un''attività di raccolta se non sono state completate tutte le attività precedenti della stessa coltivazione';
    END IF;

    UPDATE coltivazione 
    SET stato = 'IN_RACCOLTA'
    WHERE id = NEW.id_coltivazione;

  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER updates_attivita_raccolta
BEFORE UPDATE ON attivita
FOR EACH ROW
EXECUTE FUNCTION check_update_attivita_raccolta();  