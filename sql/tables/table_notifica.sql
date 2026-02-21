-- ==============================================================================================
-- Tabella notifica (includes related enum types)
-- ==============================================================================================

DROP TABLE IF EXISTS notifica CASCADE;
DROP TYPE IF EXISTS urgenza_notifica CASCADE;
DROP TYPE IF EXISTS tipo_notifica CASCADE;

CREATE TYPE urgenza_notifica AS ENUM ('BASSA', 'MEDIA', 'ALTA', 'CRITICA');
CREATE TYPE tipo_notifica AS ENUM ('NOTIFICA_PROGETTO', 'NOTIFICA_ATTIVITA_IMMINENTE');

CREATE TABLE notifica (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  data_invio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  nome_evento VARCHAR(100) NOT NULL CHECK (length(nome_evento) > 0),
  urgenza urgenza_notifica NOT NULL,
  descrizione TEXT,

  tipo tipo_notifica NOT NULL,
  giorni_mancanti INT CHECK (giorni_mancanti IS NULL OR giorni_mancanti > 0),

  id_progetto INT NOT NULL,
  id_attivita INT,

  FOREIGN KEY (id_progetto) REFERENCES progetto (id) ON DELETE CASCADE,
  FOREIGN KEY (id_attivita) REFERENCES attivita (id) ON DELETE CASCADE,

  UNIQUE (id_progetto, data_invio),
  
  CHECK (
    (tipo = 'NOTIFICA_ATTIVITA_IMMINENTE' AND id_attivita IS NOT NULL) OR
    (tipo = 'NOTIFICA_PROGETTO' AND id_attivita IS NULL)
  )
);

-- ==============================================================================================
-- Tabella riceve
-- ==============================================================================================

DROP TABLE IF EXISTS riceve CASCADE;

CREATE TABLE riceve (
  id_notifica INT NOT NULL,
  id_coltivatore INT NOT NULL,
  is_letta BOOLEAN NOT NULL,
  data_lettura TIMESTAMP,

  PRIMARY KEY (id_notifica, id_coltivatore),
  FOREIGN KEY (id_notifica) REFERENCES notifica (id) ON DELETE CASCADE,
  FOREIGN KEY (id_coltivatore) REFERENCES utente (id) ON DELETE CASCADE
);
