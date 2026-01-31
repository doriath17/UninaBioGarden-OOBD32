-- ==============================================================================================
-- Tabella transizione_attivita (includes related enum type)
-- ==============================================================================================

DROP TABLE IF EXISTS transizione_attivita CASCADE;
DROP TYPE IF EXISTS stato_attivita CASCADE;

CREATE TYPE stato_attivita AS ENUM ('pianificata', 'in_corso', 'completata', 'annullata');

CREATE TABLE transizione_attivita (
  stato_corrente stato_attivita NOT NULL,
  stato_successivo stato_attivita NOT NULL,
  PRIMARY KEY (stato_corrente, stato_successivo)
);

INSERT INTO transizione_attivita (stato_corrente, stato_successivo)
VALUES
  ('pianificata', 'in_corso'),
  ('pianificata', 'annullata'),
  ('in_corso', 'completata'),
  ('in_corso', 'annullata')
;

-- ==============================================================================================
-- Tabella attivita
-- ==============================================================================================

DROP TABLE IF EXISTS attivita CASCADE;

CREATE TABLE attivita (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  titolo VARCHAR(100) NOT NULL,

  stato stato_attivita NOT NULL DEFAULT 'pianificata', 
  note_tecniche TEXT NOT NULL,
  data_pianificazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_scadenza TIMESTAMP CHECK (data_scadenza IS NULL OR data_scadenza >= data_pianificazione),
  data_inizio TIMESTAMP CHECK (data_inizio IS NULL OR data_inizio >= data_pianificazione),
  data_fine TIMESTAMP 
  CHECK (data_fine IS NULL OR
    (data_fine >= data_pianificazione AND (data_inizio IS NULL OR data_fine >= data_inizio))),

  id_coltivazione INT NOT NULL,

  UNIQUE (id_coltivazione, titolo),
  FOREIGN KEY (id_coltivazione) REFERENCES coltivazione (id) ON DELETE CASCADE
);

-- ==============================================================================================
-- Tabella concimazione (includes related enum type)
-- ==============================================================================================

DROP TABLE IF EXISTS concimazione CASCADE;
DROP TYPE IF EXISTS t_tipo_concime CASCADE;

CREATE TYPE t_tipo_concime AS ENUM ('organico', 'minerale', 'compost');

CREATE TABLE concimazione (
  id INT NOT NULL PRIMARY KEY,

  tipo_concime t_tipo_concime NOT NULL,
  quantita_kg DECIMAL(5,2) NOT NULL CHECK (quantita_kg > 0),
  metodo_applicazione VARCHAR(100),

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);

-- ==============================================================================================
-- Tabella irrigazione (includes related enum type)
-- ==============================================================================================

DROP TABLE IF EXISTS irrigazione CASCADE;
DROP TYPE IF EXISTS t_metodo_irrigazione CASCADE;

CREATE TYPE t_metodo_irrigazione AS ENUM ('pioggia', 'goccia', 'manuale', 'scorrimento', 'nebulizzazione');

CREATE TABLE irrigazione (
  id INT NOT NULL PRIMARY KEY,

  metodo t_metodo_irrigazione NOT NULL,
  volume_acqua_l DECIMAL(5,2) CHECK (volume_acqua_l IS NULL OR volume_acqua_l > 0),

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);

-- ==============================================================================================
-- Tabella raccolta
-- ==============================================================================================

DROP TABLE IF EXISTS raccolta CASCADE;

CREATE TABLE raccolta (
  id INT NOT NULL PRIMARY KEY,

  quantita_prevista_kg DECIMAL(5,2) NOT NULL CHECK (quantita_prevista_kg > 0),
  quantita_effettiva_kg DECIMAL(5,2) CHECK (quantita_effettiva_kg IS NULL OR quantita_effettiva_kg > 0),

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);

-- ==============================================================================================
-- Tabella semina
-- ==============================================================================================

DROP TABLE IF EXISTS semina CASCADE;

CREATE TABLE semina (
  id INT NOT NULL PRIMARY KEY,

  quantita_sementi INT NOT NULL CHECK (quantita_sementi > 0),
  profondita_semina_cm DECIMAL(4,2) CHECK (profondita_semina_cm IS NULL OR ( profondita_semina_cm >= 0 AND profondita_semina_cm < 50)),

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);

-- ==============================================================================================
-- Tabella trattamento
-- ==============================================================================================

DROP TABLE IF EXISTS trattamento CASCADE;

CREATE TABLE trattamento (
  id INT NOT NULL PRIMARY KEY,

  nome_prodotto VARCHAR(50) NOT NULL CHECK (length(nome_prodotto) > 0),
  tempo_carenza INT CHECK (tempo_carenza IS NULL OR tempo_carenza > 0),
  diluzione_dose VARCHAR(20) CHECK (diluzione_dose IS NULL OR length(diluzione_dose) > 0),

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);

