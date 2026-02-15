-- ==============================================================================================
-- Tabella progetto
-- ==============================================================================================

DROP TABLE IF EXISTS progetto CASCADE;

DROP TYPE IF EXISTS stato_progetto CASCADE;

DROP TABLE IF EXISTS lavora_per CASCADE;

CREATE TYPE stato_progetto AS ENUM ('ATTIVO', 'CONCLUSO');

CREATE TABLE progetto (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  nome VARCHAR(100) NOT NULL UNIQUE,

  data_inizio     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_fine       TIMESTAMP CHECK (data_fine IS NULL OR data_fine >= data_inizio),

  stato stato_progetto NOT NULL DEFAULT 'ATTIVO',
  descrizione TEXT,

  id_proprietario INT NOT NULL,
  id_lotto INT NOT NULL,

  FOREIGN KEY (id_proprietario) REFERENCES utente (id) ON DELETE RESTRICT,
  FOREIGN KEY (id_lotto) REFERENCES lotto (id) ON DELETE RESTRICT
);


-- TODO: aggiungere i vincoli per la tabella lavora_per anche in accordo con gli stati del progetto
CREATE TABLE lavora_per (
  id_progetto INT NOT NULL,
  id_coltivatore INT NOT NULL,
  PRIMARY KEY (id_progetto, id_coltivatore),
  FOREIGN KEY (id_progetto) REFERENCES progetto (id) ON DELETE CASCADE,
  FOREIGN KEY (id_coltivatore) REFERENCES utente (id)
);

CREATE OR REPLACE VIEW view_progetti_in_corso AS
  SELECT * 
  FROM progetto 
  WHERE stato = 'ATTIVO';

CREATE OR REPLACE VIEW view_lotti_occupati AS 
  SELECT DISTINCT l.* 
  FROM view_progetti_in_corso AS p
  JOIN lotto AS l ON l.id = p.id_lotto;

CREATE OR REPLACE VIEW view_lotti_disponibili AS
  SELECT * 
  FROM lotto 
  EXCEPT 
  SELECT * 
  FROM view_lotti_occupati;

