-- VINCOLI LAVORA_PER

DROP TRIGGER IF EXISTS insert_lavora_per ON lavora_per;
DROP FUNCTION IF EXISTS check_insert_lavora_per() CASCADE;

-- ============================================================
-- INSERT -- lavora_per
-- ============================================================

CREATE OR REPLACE FUNCTION check_insert_lavora_per()
RETURNS TRIGGER AS $$
DECLARE
  v_utente utente%ROWTYPE;
BEGIN
  -- Vincolo: lavora_per_coltivatore_valido
  SELECT * INTO v_utente
  FROM utente
  WHERE id = NEW.id_coltivatore;

  IF v_utente.tipo <> 'COLTIVATORE' THEN
    RAISE EXCEPTION 'L''utente con ID = % non è un coltivatore', NEW.id_coltivatore;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_lavora_per
BEFORE INSERT ON lavora_per
FOR EACH ROW
EXECUTE FUNCTION check_insert_lavora_per();
