-- ==============================================================================================
-- Tabella lotto
-- ==============================================================================================

DROP TABLE IF EXISTS lotto CASCADE;
DROP TYPE IF EXISTS tipologia_terreno CASCADE;

CREATE TYPE tipologia_terreno AS ENUM ('ARGILLOSO', 'SABBIOSO', 'PIETROSO', 'MEDIO_IMPASTO');

CREATE TABLE lotto (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  data_registrazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  codice_lotto VARCHAR(20) NOT NULL,
  estensione_mq DECIMAL(10, 2) NOT NULL CHECK (estensione_mq > 0),
  tipologia_terreno tipologia_terreno NOT NULL,
  id_proprietario INT NOT NULL,
  id_orto INT NOT NULL,
  FOREIGN KEY (id_orto) REFERENCES orto (id) ON DELETE CASCADE,
  FOREIGN KEY (id_proprietario) REFERENCES utente (id) ON DELETE CASCADE,
  UNIQUE (id_orto, codice_lotto)
);


