-- ==============================================================================================
-- Tabella notifica (includes related enum types)
-- ==============================================================================================

DROP TABLE IF EXISTS notifica CASCADE;
DROP TYPE IF EXISTS urgenza_notifica CASCADE;
DROP TYPE IF EXISTS tipo_notifica CASCADE;

CREATE TYPE urgenza_notifica AS ENUM ('bassa', 'media', 'alta', 'critica');
CREATE TYPE tipo_notifica AS ENUM ('notifica_progetto', 'notifica_attivita_imminente');

CREATE TABLE notifica (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  data_invio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  nome_evento VARCHAR(100) NOT NULL CHECK (length(nome_evento) > 0),
  urgenza urgenza_notifica NOT NULL,
  descrizione TEXT,

  tipo tipo_notifica NOT NULL,
  giorni_mancanti INT CHECK (giorni_mancanti IS NULL OR giorni_mancanti > 0),

  id_proprietario INT NOT NULL,
  id_progetto INT NOT NULL,

  FOREIGN KEY (id_proprietario) REFERENCES utente (id),
  FOREIGN KEY (id_progetto) REFERENCES progetto (id) ON DELETE CASCADE,

  UNIQUE (id_progetto, data_invio)
);

-- ==============================================================================================
-- Tabella riceve
-- ==============================================================================================

DROP TABLE IF EXISTS riceve CASCADE;

CREATE TABLE riceve (
  id_notifica INT NOT NULL PRIMARY KEY,
  id_coltivatore INT NOT NULL,
  is_letta BOOLEAN NOT NULL,
  data_lettura TIMESTAMP,

  FOREIGN KEY (id_notifica) REFERENCES notifica (id) ON DELETE CASCADE,
  FOREIGN KEY (id_coltivatore) REFERENCES utente (id)
);
