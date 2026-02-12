

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
-- COLTURE (10 colture: 3 erbe aromatiche, 5 ortaggi, 2 alberi)
-- ==============================================================================================

-- Erbe Aromatiche
INSERT INTO coltura (nome_comune, tempo_maturazione, caratteristiche, tipo, erba_numero_tagli_stimati, erba_utilizzo_principale) VALUES
('Basilico', 30, 'Erba aromatica molto usata nella cucina italiana', 'ERBA_AROMATICA', 4, 'Cucina'),
('Rosmarino', 60, 'Pianta perenne con foglie aghiformi profumate', 'ERBA_AROMATICA', 6, 'Cucina e cosmetica'),
('Menta', 45, 'Pianta rinfrescante con molte varietà', 'ERBA_AROMATICA', 5, 'Cucina e tisane');

-- Ortaggi
INSERT INTO coltura (nome_comune, tempo_maturazione, caratteristiche, tipo, ortaggio_durata_ciclo_coltivazione, ortaggio_tipo_riproduzione) VALUES
('Pomodoro', 80, 'Ortaggio versatile, ricco di licopene', 'ORTAGGIO', 120, 'TRAPIANTO'),
('Zucchina', 50, 'Cresce rapidamente, produzione abbondante', 'ORTAGGIO', 90, 'SEMINA'),
('Lattuga', 40, 'Insalata a foglia verde tenera', 'ORTAGGIO', 60, 'SEMINA'),
('Peperone', 90, 'Ortaggio colorato, ricco di vitamina C', 'ORTAGGIO', 130, 'TRAPIANTO'),
('Melanzana', 85, 'Ortaggio viola dalla polpa spugnosa', 'ORTAGGIO', 120, 'TRAPIANTO');

-- Alberi da frutto
INSERT INTO coltura (nome_comune, tempo_maturazione, caratteristiche, tipo, albero_disposizione, albero_piantumazione) VALUES
('Limone', 365, 'Albero da frutto sempreverde, produce limoni tutto l''anno', 'ALBERO', 'ISOLATO', '2024-03-15 10:00:00'),
('Fico', 180, 'Albero rustico che produce fichi dolci', 'ALBERO', 'FILARE', '2024-02-20 09:30:00');
