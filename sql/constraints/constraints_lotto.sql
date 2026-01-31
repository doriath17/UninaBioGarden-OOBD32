-- VINCOLI LOTTO

DROP TRIGGER IF EXISTS insert_lotto ON lotto;
DROP TRIGGER IF EXISTS update_immutables_lotto ON lotto;

DROP FUNCTION IF EXISTS check_proprietario_lotto() CASCADE;
DROP FUNCTION IF EXISTS check_immutables_lotto() CASCADE;

-- ============================================================
-- INSERT -- orto posseduto solo da proprietari
-- ============================================================


CREATE OR REPLACE FUNCTION check_proprietario_lotto()
RETURNS TRIGGER AS $$ 
BEGIN
  IF NOT is_proprietario(NEW.id_proprietario) THEN
    RAISE EXCEPTION 'L''utente con ID = % non è un proprietario', NEW.id_proprietario;
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_lotto BEFORE INSERT ON lotto
  FOR EACH ROW EXECUTE FUNCTION check_proprietario_lotto();


-- ============================================================
-- UPDATE -- lotto
-- ============================================================


CREATE OR REPLACE FUNCTION check_immutables_lotto()
RETURNS TRIGGER AS $$
BEGIN

  IF NEW.id_proprietario <> OLD.id_proprietario THEN 
    RAISE EXCEPTION 'Il proprietario di un lotto non può essere modificato';
  ELSIF NEW.id_orto <> OLD.id_orto THEN 
    RAISE EXCEPTION 'L''orto in cui si trova un lotto non può essere modificato';
  ELSIF NEW.data_registrazione <> OLD.data_registrazione THEN 
    RAISE EXCEPTION 'La data di registazione di un lotto non può essere modificata';
  END IF;

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_immutables_lotto BEFORE UPDATE ON lotto
  FOR EACH ROW EXECUTE FUNCTION check_immutables_lotto();
