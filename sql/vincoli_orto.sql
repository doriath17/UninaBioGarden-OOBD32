-- VINCOLI ORTO


-- ============================================================
-- INSERT -- orto gestito solo da proprietari
-- ============================================================

CREATE OR REPLACE FUNCTION check_proprietario_orto()
RETURNS TRIGGER AS $$
DECLARE 
  v_tipo_utente utente.tipo%TYPE;
BEGIN
  SELECT u.tipo INTO v_tipo_utente
  FROM utente AS u 
  WHERE u.id = NEW.id_proprietario;

  IF v_tipo_utente <> 'proprietario' THEN 
    RAISE EXCEPTION 'L''utente % non è un proprietario e non può gestire un orto';
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_orto
BEFORE INSERT ON orto
FOR EACH ROW
EXECUTE FUNCTION check_proprietario_orto();