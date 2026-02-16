-- VINCOLI SOTTOTIPI ATTIVITA

-- ============================================================
-- SEMINA - Vincolo tipo e unicità specializzazione
-- ============================================================

DROP TRIGGER IF EXISTS check_semina_constraints ON semina;
DROP FUNCTION IF EXISTS check_semina_tipo() CASCADE;

CREATE OR REPLACE FUNCTION check_semina_tipo()
RETURNS TRIGGER AS $$
DECLARE
  v_tipo tipo_attivita;
BEGIN
  -- Verifica che il tipo dell'attività corrisponda
  SELECT tipo INTO v_tipo FROM attivita WHERE id = NEW.id;
  IF v_tipo <> 'SEMINA' THEN
    RAISE EXCEPTION 'Il tipo dell''attivita con ID = % deve essere SEMINA, ma e'' %', NEW.id, v_tipo;
  END IF;
  
  -- Verifica che non esistano altre specializzazioni
  IF EXISTS (SELECT 1 FROM irrigazione WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM concimazione WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM trattamento WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM raccolta WHERE id = NEW.id) THEN
    RAISE EXCEPTION 'L''attivita con ID = % ha gia una specializzazione', NEW.id;
  END IF;
  
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_semina_constraints
BEFORE INSERT ON semina
FOR EACH ROW
EXECUTE FUNCTION check_semina_tipo();

-- ============================================================
-- IRRIGAZIONE - Vincolo tipo e unicità specializzazione
-- ============================================================

DROP TRIGGER IF EXISTS check_irrigazione_constraints ON irrigazione;
DROP FUNCTION IF EXISTS check_irrigazione_tipo() CASCADE;

CREATE OR REPLACE FUNCTION check_irrigazione_tipo()
RETURNS TRIGGER AS $$
DECLARE
  v_tipo tipo_attivita;
BEGIN
  -- Verifica che il tipo dell'attività corrisponda
  SELECT tipo INTO v_tipo FROM attivita WHERE id = NEW.id;
  IF v_tipo <> 'IRRIGAZIONE' THEN
    RAISE EXCEPTION 'Il tipo dell''attivita con ID = % deve essere IRRIGAZIONE, ma e'' %', NEW.id, v_tipo;
  END IF;
  
  -- Verifica che non esistano altre specializzazioni
  IF EXISTS (SELECT 1 FROM semina WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM concimazione WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM trattamento WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM raccolta WHERE id = NEW.id) THEN
    RAISE EXCEPTION 'L''attivita con ID = % ha gia una specializzazione', NEW.id;
  END IF;
  
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_irrigazione_constraints
BEFORE INSERT ON irrigazione
FOR EACH ROW
EXECUTE FUNCTION check_irrigazione_tipo();

-- ============================================================
-- CONCIMAZIONE - Vincolo tipo e unicità specializzazione
-- ============================================================

DROP TRIGGER IF EXISTS check_concimazione_constraints ON concimazione;
DROP FUNCTION IF EXISTS check_concimazione_tipo() CASCADE;

CREATE OR REPLACE FUNCTION check_concimazione_tipo()
RETURNS TRIGGER AS $$
DECLARE
  v_tipo tipo_attivita;
BEGIN
  -- Verifica che il tipo dell'attività corrisponda
  SELECT tipo INTO v_tipo FROM attivita WHERE id = NEW.id;
  IF v_tipo <> 'CONCIMAZIONE' THEN
    RAISE EXCEPTION 'Il tipo dell''attivita con ID = % deve essere CONCIMAZIONE, ma e'' %', NEW.id, v_tipo;
  END IF;
  
  -- Verifica che non esistano altre specializzazioni
  IF EXISTS (SELECT 1 FROM semina WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM irrigazione WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM trattamento WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM raccolta WHERE id = NEW.id) THEN
    RAISE EXCEPTION 'L''attivita con ID = % ha gia una specializzazione', NEW.id;
  END IF;
  
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_concimazione_constraints
BEFORE INSERT ON concimazione
FOR EACH ROW
EXECUTE FUNCTION check_concimazione_tipo();

-- ============================================================
-- TRATTAMENTO - Vincolo tipo e unicità specializzazione
-- ============================================================

DROP TRIGGER IF EXISTS check_trattamento_constraints ON trattamento;
DROP FUNCTION IF EXISTS check_trattamento_tipo() CASCADE;

CREATE OR REPLACE FUNCTION check_trattamento_tipo()
RETURNS TRIGGER AS $$
DECLARE
  v_tipo tipo_attivita;
BEGIN
  -- Verifica che il tipo dell'attività corrisponda
  SELECT tipo INTO v_tipo FROM attivita WHERE id = NEW.id;
  IF v_tipo <> 'TRATTAMENTO' THEN
    RAISE EXCEPTION 'Il tipo dell''attivita con ID = % deve essere TRATTAMENTO, ma e'' %', NEW.id, v_tipo;
  END IF;
  
  -- Verifica che non esistano altre specializzazioni
  IF EXISTS (SELECT 1 FROM semina WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM irrigazione WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM concimazione WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM raccolta WHERE id = NEW.id) THEN
    RAISE EXCEPTION 'L''attivita con ID = % ha gia una specializzazione', NEW.id;
  END IF;
  
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_trattamento_constraints
BEFORE INSERT ON trattamento
FOR EACH ROW
EXECUTE FUNCTION check_trattamento_tipo();

-- ============================================================
-- RACCOLTA - Vincolo tipo e unicità specializzazione
-- ============================================================

DROP TRIGGER IF EXISTS check_raccolta_constraints ON raccolta;
DROP FUNCTION IF EXISTS check_raccolta_tipo() CASCADE;

CREATE OR REPLACE FUNCTION check_raccolta_tipo()
RETURNS TRIGGER AS $$
DECLARE
  v_tipo tipo_attivita;
BEGIN
  -- Verifica che il tipo dell'attività corrisponda
  SELECT tipo INTO v_tipo FROM attivita WHERE id = NEW.id;
  IF v_tipo <> 'RACCOLTA' THEN
    RAISE EXCEPTION 'Il tipo dell''attivita con ID = % deve essere RACCOLTA, ma e'' %', NEW.id, v_tipo;
  END IF;
  
  -- Verifica che non esistano altre specializzazioni
  IF EXISTS (SELECT 1 FROM semina WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM irrigazione WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM concimazione WHERE id = NEW.id)
     OR EXISTS (SELECT 1 FROM trattamento WHERE id = NEW.id) THEN
    RAISE EXCEPTION 'L''attivita con ID = % ha gia una specializzazione', NEW.id;
  END IF;
  
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_raccolta_constraints
BEFORE INSERT ON raccolta
FOR EACH ROW
EXECUTE FUNCTION check_raccolta_tipo();
