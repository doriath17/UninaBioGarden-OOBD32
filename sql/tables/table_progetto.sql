-- ==============================================================================================
-- Tabella progetto
-- ==============================================================================================

DROP TABLE IF EXISTS progetto CASCADE;

DROP TYPE IF EXISTS stato_progetto CASCADE;

DROP TABLE IF EXISTS lavora_per CASCADE;

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


-- TODO: aggiungere i vincoli per la tabella lavora_per anche in accordo con gli stati del progetto
CREATE TABLE lavora_per (
  id_progetto INT NOT NULL,
  id_coltivatore INT NOT NULL,
  PRIMARY KEY (id_progetto, id_coltivatore),
  FOREIGN KEY (id_progetto) REFERENCES progetto (id) ON DELETE CASCADE,
  FOREIGN KEY (id_coltivatore) REFERENCES utente (id) ON DELETE CASCADE
);

CREATE OR REPLACE VIEW vista_progetti_in_corso AS
  SELECT * 
  FROM progetto 
  WHERE stato = 'PIANIFICATO' OR stato = 'ATTIVO';

CREATE OR REPLACE VIEW vista_lotti_occupati AS 
  SELECT DISTINCT l.* 
  FROM vista_progetti_in_corso AS p
  JOIN lotto AS l ON l.id = p.id_lotto;

CREATE OR REPLACE VIEW vista_lotti_disponibili AS
  SELECT * 
  FROM lotto 
  EXCEPT 
  SELECT * 
  FROM vista_lotti_occupati;

