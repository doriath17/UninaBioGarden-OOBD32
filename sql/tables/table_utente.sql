-- ==============================================================================================
-- Tabella utente
-- ==============================================================================================

DROP TABLE IF EXISTS utente CASCADE;
DROP TYPE IF EXISTS tipo_utente CASCADE;
DROP FUNCTION IF EXISTS is_proprietario(INT) CASCADE;


CREATE TYPE tipo_utente AS ENUM ('proprietario', 'coltivatore');

CREATE TABLE utente (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  data_registrazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  nome VARCHAR(50) NOT NULL,
  cognome VARCHAR(50) NOT NULL,
  b_day DATE,
  codice_fiscale VARCHAR(16) UNIQUE,
  gender VARCHAR(10) NOT NULL,
  bio TEXT,
  tipo tipo_utente NOT NULL
);

CREATE OR REPLACE FUNCTION is_proprietario(id_utente INT)
RETURNS BOOLEAN AS $$
BEGIN 
  RETURN EXISTS (
    SELECT 1
    FROM utente
    WHERE id = id_utente AND tipo = 'proprietario'
  );
END;
$$ LANGUAGE plpgsql;