
-- VINCOLI ATTIVITA


-- ============================================================
-- immutabilità delle transizioni
-- ============================================================


CREATE OR REPLACE FUNCTION block_modification_transizione_attivita()
RETURNS TRIGGER AS $$
BEGIN
    RAISE EXCEPTION 'Le regole di transizione di un attività sono immutabili';
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER transizione_attivita_immutability
BEFORE INSERT OR UPDATE OR DELETE ON transizione_attivita
FOR EACH ROW EXECUTE FUNCTION block_modification_transizione_attivita();


-- ============================================================
-- INSERT di una attività
-- ============================================================


CREATE OR REPLACE FUNCTION check_insert_attivita()
RETURNS TRIGGER AS $$
DECLARE 
  v_stato_coltivazione coltivazione.stato_coltivazione%TYPE;
BEGIN
  IF NEW.stato <> 'pianificata' THEN 
    RAISE EXCEPTION 'Un attività appena creata deve avere stato ''pianificata''';
  END IF;

  IF NEW.data_inizio IS NOT NULL THEN 
    RAISE EXCEPTION 'Un attività appena creata deve avere ''data_inizio = NULL''';
  END IF;

  IF NEW.data_fine IS NOT NULL THEN 
    RAISE EXCEPTION 'Un attività appena creata deve avere ''data_fine = NULL''';
  END IF;

  -- blocco sulle coltivazioni in stato terminale
  SELECT stato INTO v_stato_coltivazione 
  FROM coltivazione
  WHERE id = NEW.id_coltivazione;

  IF v_stato_coltivazione IN ('fallita', 'conclusa', 'annullata') THEN
    RAISE EXCEPTION 'Impossibile associaRE l''attività ad una coltivazione terminata';
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


CREATE FUNCTION check_immutables_attivita() 
RETURNS TRIGGER AS $$ 
BEGIN 
  -- freeze dopo lo stato terminale
  IF OLD.stato IN ('annullata', 'completata') THEN 
    RAISE EXCEPTION 'Non è possibile modificare un''attività terminata';
  END IF;

  -- immutablita della data di pianificazione
  IF NEW.data_pianificazione <> OLD.data_pianificazione THEN 
    RAISE EXCEPTION '''data_pianificazione'' non può essere modificata dopo la creazine dell''attività';
  END IF;

  -- immutablita della coltivazione
  IF NEW.id_coltivazione <> OLD.id_coltivazione THEN 
    RAISE EXCEPTION 'Non è possibile modificare a quale coltivazione un''attività è associata';
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_immutables_attivita
BEFORE UPDATE ON attivita
FOR EACH ROW
EXECUTE FUNCTION check_immutables_attivita();


-- ============================================================
-- UPDATE -- transizioni di stato
-- ============================================================


CREATE FUNCTION check_stato_attivita()
RETURNS TRIGGER AS $$
DECLARE 
  v_stato_coltivazione coltivazione.stato_coltivazione%TYPE;
BEGIN
  IF NEW.stato <> OLD.stato THEN

    -- verifica se la transizione è permessa
    IF NOT EXISTS (
      SELECT 1 
      FROM transizione_attivita
      WHERE stato_corrente = OLD.stato AND stato_successivo = NEW.stato;
    ) THEN 
      RAISE EXCEPTION 'Transizione di stato non permessa';
    END IF;

    -- transizione a in_corso
    IF OLD.stato = 'pianificata' AND NEW.stato = 'in_corso' THEN
      SELECT stato INTO v_stato_coltivazione 
      FROM coltivazione
      WHERE id = NEW.id_coltivazione;

      IF v_stato_coltivazione <> 'attiva' THEN 
        RAISE EXCEPTION 'Transizione di stato non permessa: la coltivazione non è in stato ''attiva''';
      END IF;
      NEW.data_inizio := CURRENT_TIMESTAMP;

    -- terminazione attività
    ELSIF NEW.stato IN ('completata', 'annullata') THEN 
      NEW.data_fine := CURRENT_TIMESTAMP;
    END IF;

  END IF; 

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_stato_attivita
BEFORE UPDATE ON attivita
FOR EACH ROW
EXECUTE FUNCTION check_stato_attivita();
