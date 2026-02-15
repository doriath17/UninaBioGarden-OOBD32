

TRUNCATE TABLE utente, orto, lotto, coltura CASCADE;

-- ==============================================================================================
-- UTENTI (1 proprietario + 3 coltivatori)
-- ==============================================================================================

INSERT INTO utente (username, password, email, nome, cognome, b_day, codice_fiscale, gender, bio, tipo) VALUES
('mario_prop', 'password123', 'mario@example.com', 'Mario', 'Rossi', '1980-05-15', 'RSSMRA80E15F839X', 'M', 'Proprietario di orti urbani', 'PROPRIETARIO'),
('giulia_colt', 'password123', 'giulia@example.com', 'Giulia', 'Bianchi', '1992-08-22', 'BNCGLI92M62F839Y', 'F', 'Coltivatrice esperta di ortaggi', 'COLTIVATORE'),
('luca_colt', 'password123', 'luca@example.com', 'Luca', 'Verdi', '1988-03-10', 'VRDLCU88C10F839Z', 'M', 'Appassionato di agricoltura biologica', 'COLTIVATORE'),
('anna_colt', 'password123', 'anna@example.com', 'Anna', 'Neri', '1995-11-30', 'NRANNA95S70F839W', 'F', 'Specializzata in erbe aromatiche', 'COLTIVATORE');

-- ==============================================================================================
-- ORTI (3 orti per il proprietario Mario Rossi)
-- ==============================================================================================

INSERT INTO orto (nome_orto, citta, cap, via, civico, id_proprietario) VALUES
('Orto Sole', 'Napoli', '80100', 'Via Roma', '10', 1),
('Orto Luna', 'Napoli', '80121', 'Via Caracciolo', '25', 1),
('Orto Verde', 'Napoli', '80134', 'Via Toledo', '5', 1);

-- ==============================================================================================
-- LOTTI (6 lotti distribuiti tra i 3 orti)
-- ==============================================================================================

INSERT INTO lotto (codice_lotto, estensione_mq, tipologia_terreno, id_proprietario, id_orto) VALUES
('A1', 50.00, 'MEDIO_IMPASTO', 1, 1),
('A2', 75.50, 'ARGILLOSO', 1, 1),
('B1', 60.00, 'SABBIOSO', 1, 2),
('B2', 80.00, 'MEDIO_IMPASTO', 1, 2),
('C1', 45.00, 'PIETROSO', 1, 3),
('C2', 90.00, 'MEDIO_IMPASTO', 1, 3);

-- ==============================================================================================
-- COLTURE (15 colture: erbe aromatiche, ortaggi, legumi, alberi da frutto)
-- ==============================================================================================

-- Erbe Aromatiche
INSERT INTO coltura (nome_comune, tempo_maturazione, caratteristiche) VALUES
('Basilico', 60, 'Erba aromatica annuale, ideale per pesto e insalate. Richiede clima caldo e annaffiature regolari'),
('Rosmarino', 90, 'Pianta perenne sempreverde con foglie aghiformi. Resistente alla siccità, ottima per arrosti'),
('Menta', 70, 'Pianta perenne rinfrescante con crescita vigorosa. Utilizzata per tisane e cocktail'),
('Prezzemolo', 75, 'Erba biennale ricca di vitamina C. Cresce bene all''ombra parziale');

-- Ortaggi a Frutto
INSERT INTO coltura (nome_comune, tempo_maturazione, caratteristiche) VALUES
('Pomodoro San Marzano', 85, 'Varietà classica a pomodoro allungato. Ideale per conserve e sughi. Necessita di tutori'),
('Zucchina Romanesca', 55, 'Varietà precoce con frutti striati. Raccolta scalare per 2-3 mesi'),
('Peperone Quadrato', 100, 'Peperone dolce di forma cubica, ricco di antiossidanti. Maturazione da verde a rosso'),
('Melanzana Violetta', 90, 'Varietà tradizionale con buccia viola scuro. Preferisce clima caldo');

-- Ortaggi a Foglia
INSERT INTO coltura (nome_comune, tempo_maturazione, caratteristiche) VALUES
('Lattuga Romana', 65, 'Insalata croccante a cespo allungato. Ottima resistenza al calore'),
('Rucola', 30, 'Insalata dal sapore piccante. Crescita rapida, raccolta a taglio'),
('Spinacio', 45, 'Ortaggio a foglia verde ricco di ferro. Adatto a coltivazioni primaverili e autunnali');

-- Legumi
INSERT INTO coltura (nome_comune, tempo_maturazione, caratteristiche) VALUES
('Fagiolino', 60, 'Legume rampicante o nano. Baccelli teneri da consumare freschi'),
('Pisello', 70, 'Legume fresco dolce e nutriente. Preferisce clima fresco');

-- Alberi da Frutto
INSERT INTO coltura (nome_comune, tempo_maturazione, caratteristiche) VALUES
('Limone', 240, 'Agrume sempreverde che fruttifica in 8-10 mesi. Necessita di clima mite e irrigazione costante'),
('Fico', 150, 'Albero rustico con due fruttificazioni annuali. Resiste bene alla siccità');