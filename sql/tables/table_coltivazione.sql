
DROP TABLE IF EXISTS coltivazione CASCADE;
DROP TYPE IF EXISTS stato_coltivazione CASCADE;
DROP TYPE IF EXISTS stato_salute_coltivazione CASCADE;

-- ==============================================================================================
-- Tabella coltivazione
-- ==============================================================================================

CREATE TYPE stato_coltivazione AS ENUM ('ATTIVA', 'IN_RACCOLTA', 'CONCLUSA');

CREATE TYPE stato_salute_coltivazione AS ENUM ('OTTIMO', 'STABILE', 'SOFFERENTE', 'CRITICO', 'COMPROMESSO');

CREATE TABLE coltivazione (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  data_inizio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  stato stato_coltivazione NOT NULL DEFAULT 'ATTIVA',
  stato_salute stato_salute_coltivazione NOT NULL DEFAULT 'OTTIMO',
  
  note_tecniche TEXT,

  id_coltura INT NOT NULL,
  id_progetto INT NOT NULL,

  FOREIGN KEY (id_coltura) REFERENCES coltura (id) ON DELETE RESTRICT,
  FOREIGN KEY (id_progetto) REFERENCES progetto (id) ON DELETE CASCADE
);