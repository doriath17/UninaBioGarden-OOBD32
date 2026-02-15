-- ==============================================================================================
-- Tabella coltura (includes related enum types)
-- ==============================================================================================

DROP TABLE IF EXISTS coltura CASCADE;

CREATE TABLE coltura (
  id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

  nome_comune VARCHAR(100) NOT NULL UNIQUE CHECK (length(nome_comune) > 2),
  tempo_maturazione INT NOT NULL CHECK (tempo_maturazione > 0),
  caratteristiche TEXT
);
