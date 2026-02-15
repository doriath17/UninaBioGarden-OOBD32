-- VINCOLI COLTIVAZIONE

DROP TRIGGER IF EXISTS insert_coltivazione ON coltivazione;
DROP FUNCTION IF EXISTS check_insert_coltivazione() CASCADE;
DROP TRIGGER IF EXISTS update_coltivazione ON coltivazione;
DROP FUNCTION IF EXISTS check_update_coltivazione() CASCADE;

-- ============================================================
-- INSERT 
-- ============================================================

CREATE OR REPLACE FUNCTION 
check_insert_coltivazione()
RETURNS TRIGGER AS $$
DECLARE
  v_progetto progetto%ROWTYPE;
BEGIN

  IF NEW.stato IS NOT NULL AND NEW.stato <> 'ATTIVA' THEN
    RAISE EXCEPTION 'Una nuova coltivazione deve avere ''stato = ATTIVA''';
  END IF;

  IF NEW.data_fine IS NOT NULL THEN 
    RAISE EXCEPTION 'Un attività appena creata deve avere ''data_fine = NULL''';
  END IF;

  SELECT * INTO v_progetto
  FROM progetto
  WHERE id = NEW.id_progetto;

  IF v_progetto.stato <> 'ATTIVO' THEN 
    RAISE EXCEPTION 'Impossibile creare una coltivazione per un progetto non attivo';
  END IF;

  IF NEW.data_inizio IS NOT NULL AND NEW.data_inizio < v_progetto.data_inizio THEN 
    RAISE EXCEPTION 'La data di inizio di una coltivazione non può essere precedente alla data di inizio del progetto';
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_coltivazione
BEFORE INSERT ON coltivazione
FOR EACH ROW
EXECUTE FUNCTION check_insert_coltivazione();

-- ============================================================
-- UPDATE 
-- ============================================================

CREATE OR REPLACE FUNCTION
check_update_coltivazione()
RETURNS TRIGGER AS $$
BEGIN

  IF OLD.stato = 'CONCLUSA' THEN 
    RAISE EXCEPTION 'Impossibile modificare una coltivazione terminata';
  END IF;

  IF NEW.data_inizio <> OLD.data_inizio THEN 
    RAISE EXCEPTION 'Impossibile modificare la data di inizio della coltivazione';
  END IF;

  IF NEW.id_progetto <> OLD.id_progetto THEN 
    RAISE EXCEPTION 'Impossibile modificare il progetto della coltivazione';
  END IF;

  IF NEW.id_coltura <> OLD.id_coltura THEN 
    RAISE EXCEPTION 'Impossibile modificare la coltura della coltivazione';
  END IF;

  IF NEW.stato = 'CONCLUSA' AND NEW.data_fine IS NULL THEN 
    NEW.data_fine := CURRENT_TIMESTAMP;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_coltivazione
BEFORE UPDATE ON coltivazione
FOR EACH ROW
EXECUTE FUNCTION check_update_coltivazione();