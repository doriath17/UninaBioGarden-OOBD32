-- VINCOLI ORTO

DROP TRIGGER IF EXISTS insert_orto ON orto;
DROP FUNCTION IF EXISTS check_proprietario_orto() CASCADE;

-- ============================================================
-- INSERT -- orto gestito solo da proprietari
-- ============================================================

CREATE OR REPLACE FUNCTION check_proprietario_orto()
RETURNS TRIGGER AS $$
DECLARE 
  v_utente utente%ROWTYPE;
BEGIN
  SELECT * INTO v_utente
  FROM utente AS u 
  WHERE u.id = NEW.id_proprietario;

  IF v_utente.tipo <> 'proprietario' THEN 
    RAISE EXCEPTION 'L''utente % non è un proprietario e non può gestire un orto', utente.username;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_orto
BEFORE INSERT ON orto
FOR EACH ROW
EXECUTE FUNCTION check_proprietario_orto();
