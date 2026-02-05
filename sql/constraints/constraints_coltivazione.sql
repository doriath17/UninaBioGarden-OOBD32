-- VINCOLI COLTIVAZIONE

DROP TRIGGER IF EXISTS transizione_stato_immutability ON transizione_stato;


DROP FUNCTION IF EXISTS block_modification_transizione_stato() CASCADE;

-- ============================================================
-- immutabilità delle transizioni
-- ============================================================

CREATE OR REPLACE FUNCTION 
block_modification_transizione_stato()
RETURNS TRIGGER AS $$
BEGIN
  RAISE EXCEPTION 'Le regole di transizione dello stato della coltivazione sono immutabili';
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER transizione_stato_immutability
BEFORE INSERT OR UPDATE OR DELETE 
ON transizione_stato
FOR EACH ROW 
EXECUTE FUNCTION block_modification_transizione_stato();


-- ============================================================
-- INSERT -- stato iniziale
-- ============================================================

CREATE OR REPLACE FUNCTION 
check_insert_coltivazione()
RETURNS TRIGGER AS $$
DECLARE
  v_data_creazione_progetto progetto.data_creazione%TYPE;
BEGIN

  -- coerenza stato iniziale
  IF NEW.stato <> 'pianificata' OR NEW.stato_salute <> 'ottimo' THEN
    RAISE EXCEPTION 'Una nuova coltivazione deve avere ''stato = pianificata'' e ''stato_salute = ottimo''';
  END IF;

    -- attributi che dovrebbero essere NULL all'inserimento
  IF NEW.data_inizio IS NOT NULL THEN 
    RAISE EXCEPTION 'Un coltivazione appena creata deve avere ''data_inizio = NULL''';
  END IF;

  IF NEW.data_fine IS NOT NULL THEN 
    RAISE EXCEPTION 'Un attività appena creata deve avere ''data_fine = NULL''';
  END IF;

  -- se l utente cerca di inserire la data di creazione manualmente
  -- questa viene ignorata e si usa il current timestamp. Questo 
  -- approccio garantisce che la data_creazione >= progetto.data_creazione
  -- dove progetto è il progetto associato
  IF NEW.data_creazione IS NOT NULL THEN 
    RAISE NOTICE 'Attenzione: la data creazione inserita % verrà ignorata, il sistema utilizzerà il timestamp attuale', NEW.data_creazione;
  END IF;
  NEW.data_creazione := CURRENT_TIMESTAMP;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER stato_iniziale_coltivazione
BEFORE INSERT ON coltivazione
FOR EACH ROW
EXECUTE FUNCTION check_insert_coltivazione();

-- ============================================================
-- UPDATE attributi immutabili
-- ============================================================

CREATE OR REPLACE FUNCTION
check_immutables_coltivazione()
RETURNS TRIGGER AS $$
BEGIN

  -- freeze dopo la terminazione
  IF OLD.stato IN ('conclusa', 'fallita', 'annullata') THEN 
    RAISE EXCEPTION 'Impossibile modificare una coltivazione terminata';
  END IF;

  IF NEW.data_creazione <> OLD.data_creazione THEN 
    RAISE EXCEPTION 'Impossibile modificare la data di creazione della coltivazione';
  END IF;

  IF NEW.id_progetto <> OLD.id_progetto THEN 
    RAISE EXCEPTION 'Impossibile modificare il progetto della coltivazione';
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_immutables_coltivazione
BEFORE UPDATE ON coltivazione
FOR EACH ROW
EXECUTE FUNCTION check_immutables_coltivazione();

-- ============================================================
-- UPDATE -- transizioni di stato
-- ============================================================

CREATE OR REPLACE FUNCTION
check_update_coltivazione()
RETURNS TRIGGER AS $$
DECLARE 
  v_stato_progetto progetto.stato%TYPE;
BEGIN
  IF NEW.stato <> OLD.stato THEN
    -- verifica se le transizione è permessa
    IF NOT EXISTS (
      SELECT 1 
      FROM transizione_stato
      WHERE stato_corrente = OLD.stato AND stato_successivo = NEW.stato
    ) THEN 
      RAISE EXCEPTION 'Transizione di stato (%, %) non permessa', OLD.stato
      , NEW.stato;
    END IF;

    IF OLD.stato = 'pianificata' AND NEW.stato = 'attiva' THEN
      SELECT stato INTO v_stato_progetto 
      FROM PROGETTO
      WHERE id = OLD.id_progetto;

      IF v_stato_progetto <> 'attivo' THEN 
        RAISE EXCEPTION 'Transizione di stato (%, %) non permessa: il progetto non è in stato ''attivo''', OLD.stato, NEW.stato;
      END IF;

    -- terminazione coltivazione
    ELSID NEW.stato IN ('conclusa', 'fallita', 'annullata') THEN
      IF NEW.data_fine IS NOT NULL THEN
        RAISE NOTICE 'Attenzione: la data fine inserita % verrà ignorata, il sistema utilizzerà il timestamp attuale', NEW.data_creazione;
      END IF;
      NEW.data_fine := CURRENT_TIMESTAMP;
    END IF;
  END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_immutables_coltivazione
BEFORE UPDATE ON coltivazione 
FOR EACH ROW 
EXECUTE FUNCTION check_update_coltivazione();