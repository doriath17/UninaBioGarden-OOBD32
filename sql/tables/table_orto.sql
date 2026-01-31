-- ==============================================================================================
-- Tabella orto
-- ==============================================================================================

DROP TABLE IF EXISTS orto CASCADE;

CREATE TABLE orto (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  data_registrazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  nome_orto VARCHAR(50) NOT NULL UNIQUE CHECK (length(nome_orto) >= 2),
  citta VARCHAR(50) NOT NULL,
  cap CHAR(5) NOT NULL CHECK (cap ~ '^[0-9]{5}$'),
  via VARCHAR(100) NOT NULL,
  civico VARCHAR(10),
  id_proprietario INT NOT NULL,
  
  FOREIGN KEY (id_proprietario) REFERENCES utente (id) ON DELETE CASCADE
);
