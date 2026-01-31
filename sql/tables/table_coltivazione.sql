-- ==============================================================================================
-- Tabella coltivazione (includes related enum types)
-- ==============================================================================================

DROP TABLE IF EXISTS coltivazione CASCADE;
DROP TYPE IF EXISTS t_stato_coltivazione CASCADE;
DROP TYPE IF EXISTS stato_salute_coltivazione CASCADE;

CREATE TYPE t_stato_coltivazione AS ENUM ('pianificata', 'attiva', 'conclusa', 'fallita', 'annullata');
CREATE TYPE stato_salute_coltivazione AS ENUM ('ottimo', 'stabile', 'sofferente', 'critico', 'compromesso');

CREATE TABLE coltivazione (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  stato_coltivazione t_stato_coltivazione NOT NULL,
  stato_salute stato_salute_coltivazione NOT NULL,
  
  data_creazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_inizio TIMESTAMP,
  
  quantita_piante INT NOT NULL,
  note_tecniche TEXT,

  id_coltura INT NOT NULL,
  id_progetto INT NOT NULL,

  FOREIGN KEY (id_coltura) REFERENCES coltura (id), -- TODO: valuta se fare DELETE ON CASCADE
  FOREIGN KEY (id_progetto) REFERENCES progetto (id) ON DELETE CASCADE
);
