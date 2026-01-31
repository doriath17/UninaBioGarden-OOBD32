-- ==============================================================================================
--
-- Tabella utente
--
-- ==============================================================================================

CREATE TYPE tipo_utente AS ENUM ('proprietario', 'coltivatore');

CREATE TABLE utente (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  data_registrazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  nome VARCHAR(50) NOT NULL,
  cognome VARCHAR(50) NOT NULL,
  b_day DATE,
  codice_fiscale VARCHAR(16) UNIQUE,
  gender VARCHAR(10) NOT NULL,
  bio TEXT,
  tipo tipo_utente NOT NULL
);

CREATE OR REPLACE FUNCTION is_proprietario(id_utente INT)
RETURNS BOOLEAN AS $$
BEGIN 
  RETURN EXISTS (
    SELECT 1
    FROM utente
    WHERE id = is_utente AND tipo = 'proprietario'
  );
END;
$$ LANGUAGE plpgsql;

-- ==============================================================================================
--
-- Tabella orto
--
-- ==============================================================================================

-- TODO: view con estensione totale in mq

CREATE TABLE orto (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  data_registrazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  nome_orto VARCHAR(50) NOT NULL UNIQUE CHECK (length(nome_orto) >= 2),
  citta VARCHAR(50) NOT NULL,
  cap CHAR(5) NOT NULL CHECK (cap ~ '^[0-9]{5}$'),
  via VARCHAR(100) NOT NULL,
  civico VARCHAR(10),
  id_proprietario INT NOT NULL,
  
  FOREIGN KEY (id_proprietario) REFERENCES utente (id) ON DELETE CASCADE
);

-- ==============================================================================================
--
-- Tabella lotto
--
-- ==============================================================================================

CREATE TABLE lotto (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  data_registrazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  codice_lotto VARCHAR(20) NOT NULL,
  estensione_mq DECIMAL(10, 2) NOT NULL CHECK (estensione_mq > 0),
  id_proprietario INT NOT NULL,
  id_orto INT NOT NULL,

  FOREIGN KEY (id_orto) REFERENCES orto (id) ON DELETE CASCADE,
  FOREIGN KEY (id_proprietario) REFERENCES utente (id) ON DELETE CASCADE,
  UNIQUE (id_orto, codice_lotto)
);


-- ==============================================================================================
-- Tabella Progetto
-- ==============================================================================================


CREATE TABLE progetto (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  data_creazione  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_inizio     TIMESTAMP,
  data_fine       TIMESTAMP,

  nome VARCHAR(100) NOT NULL UNIQUE,
  descrizione TEXT,

  id_proprietario INT NOT NULL,
  id_lotto INT NOT NULL,

  FOREIGN KEY (id_proprietario) REFERENCES utente (id) DELETE ON CASCADE,
  FOREIGN KEY (id_lotto) REFERENCES lotto (id) DELETE ON CASCADE
);


-- ==============================================================================================
-- Tabella coltura
-- ==============================================================================================


CREATE TYPE tipo_coltura AS ENUM ('erba_aromatica', 'ortaggio', 'albero');
CREATE TYPE tipo_riproduzione_ortaggio AS ENUM ('semina', 'trapianto');

CREATE TABLE coltura (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  nome_comune VARCHAR(100) NOT NULL UNIQUE CHECK (length(nome_comune) > 2),
  tempo_maturazione INT NOT NULL CHECK (tempo_maturazione > 0),
  caratteristiche TEXT,

  tipo tipo_coltura NOT NULL,

  -- se tipo == erba aromatica
  erba_numero_tagli_stimati INT CHECK (erba_numero_tagli_stimati IS NULL OR erba_numero_tagli_stimati > 0),
  erba_utilizzo_principale VARCHAR(100) CHECK (nome_comune IS NULL OR length(nome_comune) > 2),

  -- se tipo == ortaggio
  ortaggio_durata_ciclo_coltivazione INT CHECK (ortaggio_durata_ciclo_coltivazione IS NULL OR ortaggio_durata_ciclo_coltivazione > 0),
  ortaggio_tipo_riproduzione tipo_riproduzione_ortaggio,

  -- se tipo == albero
  albero_disposizione VARCHAR(20),
  albero_piantumazione TIMESTAMP
);


-- ==============================================================================================
-- Tabella coltivazione
-- ==============================================================================================


CREATE TYPE t_stato_coltivazione AS ENUM ('pianificata', 'attiva', 'conclusa', 'fallita', 'annullata');
CREATE TYPE stato_salute_coltivazione AS ENUM ('ottimo', 'stabile', 'sofferente', 'critico', 'compromesso');

-- TODO: view con attributi derivati

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


-- ==============================================================================================
-- Gerarchia Attivita
-- ==============================================================================================


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

-- TODO: fare la view con in_scadenza 

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

CREATE TABLE semina (
  id INT NOT NULL PRIMARY KEY,

  quantita_sementi INT NOT NULL CHECK (quantita_sementi > 0),
  profondita_semina_cm DECIMAL(4,2) CHECK (profondita_semina_cm IS NULL OR ( profondita_semina_cm >= 0 AND profondita_semina_cm < 50)),

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);

CREATE TABLE trattamento (
  id INT NOT NULL PRIMARY KEY,

  nome_prodotto VARCHAR(50) NOT NULL CHECK (length(nome_prodotto) > 0),
  tempo_carenza INT CHECK (tempo_carenza IS NULL OR tempo_carenza > 0),
  diluzione_dose VARCHAR(20) CHECK (diluzione_dose IS NULL OR length(diluzione_dose) > 0),

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);

CREATE TYPE t_tipo_concime AS ENUM ('organico', 'minerale', 'compost');

CREATE TABLE concimazione (
  id INT NOT NULL PRIMARY KEY,

  tipo_concime t_tipo_concime NOT NULL,
  quantita_kg DECIMAL(5,2) NOT NULL CHECK (quantita_kg > 0),
  metodo_applicazione VARCHAR(100),

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);

CREATE TYPE t_metodo_irrigazione AS ENUM ('pioggia', 'goccia', 'manuale', 'scorrimento', 'nebulizzazione'); 

CREATE TABLE irrigazione (
  id INT NOT NULL PRIMARY KEY,

  metodo t_metodo_irrigazione NOT NULL,
  volume_acqua_l DECIMAL(5,2) CHECK (volume_acqua_l IS NULL OR volume_acqua_l > 0),

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);


CREATE TABLE raccolta (
  id INT NOT NULL PRIMARY KEY,

  quantita_prevista_kg DECIMAL(5,2) NOT NULL CHECK (quantita_prevista_kg > 0),
  quantita_effettiva_kg DECIMAL(5,2) CHECK (quantita_effettiva_kg IS NULL OR quantita_effettiva_kg > 0),

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);


-- ==============================================================================================
-- Tabella Notifica
-- ==============================================================================================


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

-- TODO: valuta un nome migliore
CREATE TABLE riceve (
  id_notifica INT NOT NULL PRIMARY KEY,
  id_coltivatore INT NOT NULL,
  is_letta BOOLEAN NOT NULL,
  data_lettura TIMESTAMP,

  FOREIGN KEY (id_notifica) REFERENCES notifica (id) ON DELETE CASCADE,
  FOREIGN KEY (id_coltivatore) REFERENCES utente (id)
);
