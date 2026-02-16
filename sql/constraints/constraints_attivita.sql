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
  -- Vincolo: attivita_nuova_pianificata
  IF NEW.stato <> 'PIANIFICATA' THEN 
    RAISE EXCEPTION 'Un attività appena creata deve avere stato ''PIANIFICATA''';
  END IF;

  -- Vincolo: attivita_nuova_pianificata
  IF NEW.data_fine IS NOT NULL THEN 
    RAISE EXCEPTION 'Un attività appena creata deve avere ''data_fine = NULL''';
  END IF;

  -- Vincolo: attivita_coltivazione_non_terminale
  -- blocco sulle coltivazioni in stato terminale
  SELECT stato INTO v_stato_coltivazione 
  FROM coltivazione
  WHERE id = NEW.id_coltivazione;

  IF v_stato_coltivazione IN ('CONCLUSA', 'IN_RACCOLTA') THEN
    RAISE EXCEPTION 'Impossibile inserire una nuova attività ad una coltivazione conclusa o in raccolta';
  END IF;

  -- Vincolo: attivita_coltivatore_valido
  -- check sul coltivatore
  SELECT * INTO v_coltivatore
  FROM utente
  WHERE id = NEW.id_coltivatore;

  IF v_coltivatore.tipo <> 'COLTIVATORE' THEN 
    RAISE EXCEPTION 'L''utente con ID = % non è un coltivatore', NEW.id_coltivatore;
  END IF;

  -- Vincolo: attivita_coltivatore_valido
  IF NOT EXISTS (
    SELECT 1
    FROM lavora_per lp
    JOIN coltivazione c ON c.id_progetto = lp.id_progetto
    WHERE lp.id_coltivatore = NEW.id_coltivatore
    AND c.id = NEW.id_coltivazione
  ) THEN
    RAISE EXCEPTION 'Il coltivatore con ID = % non è assegnato al progetto della coltivazione %', NEW.id_coltivatore, NEW.id_coltivazione;
  END IF;

  -- Vincolo: coltivazione_unica_raccolta
  IF NEW.tipo = 'RACCOLTA' THEN
    IF EXISTS (
      SELECT 1
      FROM attivita a
      WHERE a.id_coltivazione = NEW.id_coltivazione
      AND a.tipo = 'RACCOLTA'
    ) THEN
      RAISE EXCEPTION 'Una coltivazione può avere una sola attività di tipo Raccolta';
    END IF;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_attivita
BEFORE INSERT ON attivita
FOR EACH ROW
EXECUTE FUNCTION check_insert_attivita();


-- ============================================================
-- UPDATE 
-- ============================================================


CREATE FUNCTION check_update_attivita() 
RETURNS TRIGGER AS $$ 
BEGIN 
  -- Vincolo: attivita_completata_readonly
  IF OLD.stato = 'COMPLETATA' THEN 
    RAISE EXCEPTION 'Non è possibile modificare un''attività terminata';
  END IF;

  -- Vincolo: attivita_campi_immutabili
  IF NEW.data_pianificazione <> OLD.data_pianificazione THEN 
    RAISE EXCEPTION '''data_pianificazione'' non può essere modificata dopo la creazine dell''attività';
  END IF;

  -- Vincolo: attivita_campi_immutabili
  IF NEW.id_coltivazione <> OLD.id_coltivazione THEN 
    RAISE EXCEPTION 'Non è possibile modificare a quale coltivazione un''attività è associata';
  END IF;

  -- Vincolo: attivita_transizioni_stato
  IF OLD.stato = 'PIANIFICATA' AND NEW.stato = 'IN_CORSO' AND NEW.data_inizio IS NULL THEN 
    NEW.data_inizio := CURRENT_TIMESTAMP;
  END IF;

  -- Vincolo: attivita_transizioni_stato
  IF NEW.stato = 'PIANIFICATA' AND OLD.stato = 'IN_CORSO' THEN 
    RAISE EXCEPTION 'Non è possibile riportare un''attività in stato ''PIANIFICATA''';
  END IF;

  -- Vincolo: attivita_transizioni_stato
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
BEGIN
  -- Vincolo: coltivazione_transizione_raccolta
  -- vedi se esiste un attivita di raccolta e se questa sta passando allo stato IN_CORSO
  IF EXISTS (
    SELECT 1 
    FROM raccolta 
    WHERE id = OLD.id
  ) AND NEW.stato = 'IN_CORSO' AND OLD.stato = 'PIANIFICATA' THEN
    IF EXISTS (
      SELECT 1 
      FROM attivita 
      WHERE stato <> 'COMPLETATA' 
        AND id_coltivazione = OLD.id_coltivazione 
        AND id <> OLD.id
    ) THEN 
      RAISE EXCEPTION 'Non è possibile iniziare un''attività di raccolta se non sono state completate tutte le attività precedenti della stessa coltivazione';
    END IF;

    UPDATE coltivazione 
    SET stato = 'IN_RACCOLTA'
    WHERE id = NEW.id_coltivazione;

  END IF;

  -- se l'attività di raccolta passa da IN_CORSO a COMPLETATA, chiudi la coltivazione
  IF EXISTS (
    SELECT 1
    FROM raccolta
    WHERE id = OLD.id
  ) AND NEW.stato = 'COMPLETATA' AND OLD.stato = 'IN_CORSO' THEN
    -- Imposta lo stato della coltivazione a CONCLUSA
    UPDATE coltivazione
    SET stato = 'CONCLUSA'
    WHERE id = NEW.id_coltivazione;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER updates_attivita_raccolta
BEFORE UPDATE ON attivita
FOR EACH ROW
EXECUTE FUNCTION check_update_attivita_raccolta();  




