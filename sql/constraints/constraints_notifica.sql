-- VINCOLI NOTIFICA

DROP TRIGGER IF EXISTS insert_riceve ON riceve;
DROP FUNCTION IF EXISTS check_insert_riceve() CASCADE;

-- ============================================================
-- INSERT -- notifica
-- ============================================================

CREATE OR REPLACE FUNCTION
check_insert_notifica()
RETURNS TRIGGER AS $$
BEGIN
  -- Vincolo: notifica_tipo_attivita_coerenza
  IF NEW.tipo = 'NOTIFICA_ATTIVITA_IMMINENTE' AND NEW.id_attivita IS NULL THEN
    RAISE EXCEPTION 'Una notifica di tipo ''NOTIFICA_ATTIVITA_IMMINENTE'' deve essere associata a un''attività';
  ELSIF NEW.tipo = 'NOTIFICA_PROGETTO' AND NEW.id_attivita IS NOT NULL THEN
    RAISE EXCEPTION 'Una notifica di tipo ''NOTIFICA_PROGETTO'' non può essere associata a un''attività';
  END IF;

  -- Vincolo: notifica_attivita_stesso_progetto
  IF NEW.tipo = 'NOTIFICA_ATTIVITA_IMMINENTE' THEN
    IF NOT EXISTS (
      SELECT 1
      FROM attivita a
      JOIN coltivazione c ON c.id = a.id_coltivazione
      WHERE a.id = NEW.id_attivita
      AND c.id_progetto = NEW.id_progetto
  ) THEN
      RAISE EXCEPTION 'L''attività associata alla notifica non appartiene al progetto specificato';
    END IF;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_notifica
BEFORE INSERT ON notifica
FOR EACH ROW
EXECUTE FUNCTION check_insert_notifica();

-- ============================================================
-- INSERT -- riceve
-- ============================================================

CREATE OR REPLACE FUNCTION check_insert_riceve()
RETURNS TRIGGER AS $$
DECLARE 
  v_notifica notifica%ROWTYPE;
BEGIN
  SELECT *
  INTO v_notifica
  FROM notifica 
  WHERE id = NEW.id_notifica;

  IF v_notifica.id IS NULL THEN
    RAISE EXCEPTION 'Notifica % inesistente', NEW.id_notifica;
  END IF;

  -- Vincolo: riceve_notifica_progetto_multipla / notifica_attivita_imminente_riceve_coltivatore
  -- IF v_notifica.tipo = 'NOTIFICA_ATTIVITA_IMMINENTE' THEN 
    -- RAISE EXCEPTION 'Non è possibile inserire una notifica di tipo ''NOTIFICA_ATTIVITA_IMMINENTE'' nella tabella riceve';
  -- END IF;

  -- Vincolo: riceve_coltivatore_progetto
  IF NOT EXISTS (
    SELECT 1
    FROM lavora_per lp
    WHERE lp.id_coltivatore = NEW.id_coltivatore
      AND lp.id_progetto = v_notifica.id_progetto
  ) THEN
    RAISE EXCEPTION 'Il coltivatore con ID = % non è assegnato al progetto della notifica %', NEW.id_coltivatore, NEW.id_notifica;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_riceve
BEFORE INSERT ON riceve
FOR EACH ROW
EXECUTE FUNCTION check_insert_riceve();