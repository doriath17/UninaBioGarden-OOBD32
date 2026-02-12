
DROP TABLE IF EXISTS coltivazione CASCADE;
DROP TABLE IF EXISTS transizione_stato_coltivazione CASCADE;
DROP TYPE IF EXISTS stato_coltivazione CASCADE;
DROP TYPE IF EXISTS stato_salute_coltivazione CASCADE;

-- ==============================================================================================
-- Tabella transizione dello stato della coltivazione
-- ==============================================================================================

CREATE TYPE stato_coltivazione AS ENUM ('PIANIFICATA', 'ATTIVA', 'CONCLUSA');

CREATE TABLE transizione_stato_coltivazione (
  stato_corrente stato_coltivazione NOT NULL,
  stato_successivo stato_coltivazione NOT NULL,
  PRIMARY KEY (stato_corrente, stato_successivo)
);


INSERT INTO transizione_stato_coltivazione (stato_corrente, stato_successivo) 
VALUES
('PIANIFICATA', 'ATTIVA'),
('PIANIFICATA', 'CONCLUSA'),
('ATTIVA', 'CONCLUSA');
-- ==============================================================================================
-- Tabella coltivazione
-- ==============================================================================================


-- non vi è la necessita di una tabella per definire le transizioni possibili:
-- ad eccezione dello stato 'compromesso', si può passare liberamente da uno stato 
-- all'altro. 
CREATE TYPE stato_salute_coltivazione AS ENUM ('OTTIMO', 'STABILE', 'SOFFERENTE', 'CRITICO', 'COMPROMESSO');


CREATE TABLE coltivazione (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  stato stato_coltivazione NOT NULL,
  stato_salute stato_salute_coltivazione NOT NULL,
  
  data_creazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_inizio TIMESTAMP,
  data_fine TIMESTAMP,
  
  quantita_piante INT NOT NULL,
  note_tecniche TEXT,

  id_coltura INT NOT NULL,
  id_progetto INT NOT NULL,

  FOREIGN KEY (id_coltura) REFERENCES coltura (id), -- TODO: valuta se fare DELETE ON CASCADE
  FOREIGN KEY (id_progetto) REFERENCES progetto (id) ON DELETE CASCADE
);
