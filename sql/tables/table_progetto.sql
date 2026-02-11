-- ==============================================================================================
-- Tabella progetto
-- ==============================================================================================

DROP TABLE IF EXISTS progetto CASCADE;

DROP TYPE IF EXISTS stato_progetto CASCADE;

CREATE TYPE stato_progetto AS ENUM ('PIANIFICATO', 'ATTIVO', 'FALLITO', 'CONCLUSO');

CREATE TABLE progetto (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  data_creazione  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_inizio     TIMESTAMP,
  data_fine       TIMESTAMP,

  nome VARCHAR(100) NOT NULL UNIQUE,
  descrizione TEXT,
  stato stato_progetto NOT NULL,

  id_proprietario INT NOT NULL,
  id_lotto INT NOT NULL,

  FOREIGN KEY (id_proprietario) REFERENCES utente (id) ON DELETE CASCADE,
  FOREIGN KEY (id_lotto) REFERENCES lotto (id) ON DELETE CASCADE
);
