-- ==============================================================================================
-- Tabella attivita
-- ==============================================================================================

DROP TABLE IF EXISTS attivita CASCADE;
DROP TYPE IF EXISTS stato_attivita CASCADE;
DROP TYPE IF EXISTS tipo_attivita CASCADE;

CREATE TYPE stato_attivita AS ENUM ('PIANIFICATA', 'IN_CORSO', 'COMPLETATA');
CREATE TYPE tipo_attivita AS ENUM ('SEMINA', 'IRRIGAZIONE', 'CONCIMAZIONE', 'TRATTAMENTO', 'RACCOLTA');

CREATE TABLE attivita (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,

  data_pianificazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_inizio TIMESTAMP CHECK (data_inizio IS NULL OR data_inizio::date >= data_pianificazione::date),
  data_scadenza TIMESTAMP CHECK (data_scadenza IS NULL OR data_scadenza::date >= data_pianificazione::date),
  data_fine TIMESTAMP CHECK (data_fine IS NULL OR data_fine::date >= data_inizio::date),
  CHECK (data_fine IS NULL OR data_fine::date >= data_inizio::date),

  stato stato_attivita NOT NULL DEFAULT 'PIANIFICATA', 
  tipo tipo_attivita NOT NULL,
  note_tecniche TEXT NOT NULL,

  id_coltivazione INT NOT NULL,
  id_coltivatore INT NOT NULL,

  UNIQUE (id_coltivazione, nome),
  FOREIGN KEY (id_coltivazione) REFERENCES coltivazione (id) ON DELETE CASCADE,
  FOREIGN KEY (id_coltivatore) REFERENCES utente (id) ON DELETE RESTRICT
);

CREATE OR REPLACE FUNCTION is_in_scadenza(in_data_inizio TIMESTAMP, in_data_scadenza TIMESTAMP)
RETURNS BOOLEAN AS $$
DECLARE 
  v_total_duration INTERVAL;
  v_time_left INTERVAL;
BEGIN
  v_total_duration := in_data_scadenza - in_data_inizio;
  v_time_left := in_data_scadenza - CURRENT_TIMESTAMP;

  -- l'attività è considerata "in scadenza" se è rimasto il 20% del tempo totale o meno, ma non più di 5 giorni
  -- il limite del 20% serve a identificare attività che hanno una durata totale minore dei 5 giorni, mentre il limite di 5 giorni evita di considerare attività con scadenze molto lontane (il cui 20% potrebbe essere un valore molto alto) 
  IF v_time_left <= (v_total_duration * 0.2) AND v_time_left < INTERVAL '5 days' THEN
    RETURN TRUE;
  ELSE
    RETURN FALSE;
  END IF;
END;  
$$ LANGUAGE plpgsql;

CREATE VIEW view_attivita AS 
  SELECT *, is_in_scadenza(data_inizio, data_scadenza) AS in_scadenza
  FROM attivita;

-- ==============================================================================================
-- Tabella concimazione (includes related enum type)
-- ==============================================================================================

DROP TABLE IF EXISTS concimazione CASCADE;
DROP TYPE IF EXISTS t_tipo_concime CASCADE;

CREATE TYPE t_tipo_concime AS ENUM ('ORGANICO', 'MINERALE', 'COMPOST');

CREATE TABLE concimazione (
  id INT NOT NULL PRIMARY KEY,

  tipo_concime t_tipo_concime NOT NULL,
  quantita_kg DECIMAL(5,2) NOT NULL CHECK (quantita_kg > 0),

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);

-- ==============================================================================================
-- Tabella irrigazione (includes related enum type)
-- ==============================================================================================

DROP TABLE IF EXISTS irrigazione CASCADE;
DROP TYPE IF EXISTS t_metodo_irrigazione CASCADE;

CREATE TYPE t_metodo_irrigazione AS ENUM ('PIOGGIA', 'GOCCIA', 'MANUALE', 'SCORRIMENTO', 'NEBULIZZAZIONE');

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

  FOREIGN KEY (id) REFERENCES attivita (id) ON DELETE CASCADE 
);

