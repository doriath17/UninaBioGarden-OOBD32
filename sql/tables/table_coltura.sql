-- ==============================================================================================
-- Tabella coltura (includes related enum types)
-- ==============================================================================================

DROP TABLE IF EXISTS coltura CASCADE;
DROP TYPE IF EXISTS tipo_coltura CASCADE;
DROP TYPE IF EXISTS tipo_riproduzione_ortaggio CASCADE;

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
