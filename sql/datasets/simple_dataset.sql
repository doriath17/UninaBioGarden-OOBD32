

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

-- ==============================================================================================
-- PROGETTI (4 progetti con stati diversi)
-- ==============================================================================================

INSERT INTO progetto (nome, descrizione, stato, data_inizio, data_fine, id_proprietario, id_lotto) VALUES
('Orto Primavera 2025', 'Coltivazione di ortaggi stagionali primaverili', 'ATTIVO', '2025-03-01', NULL, 1, 1),
('Erbe Aromatiche Bio', 'Progetto dedicato alla coltivazione biologica di erbe aromatiche', 'ATTIVO', '2025-02-15', NULL, 1, 3),
('Orto Estivo 2024', 'Progetto estivo con ortaggi da frutto', 'CONCLUSO', '2024-06-01', '2024-10-15', 1, 2),
('Legumi Autunnali', 'Coltivazione di legumi per l''autunno', 'ATTIVO', '2025-09-01', NULL, 1, 4);

-- ==============================================================================================
-- ASSOCIAZIONI LAVORA_PER (coltivatori assegnati ai progetti)
-- ==============================================================================================

INSERT INTO lavora_per (id_progetto, id_coltivatore) VALUES
(1, 2), -- Giulia lavora su Orto Primavera 2025
(1, 3), -- Luca lavora su Orto Primavera 2025
(2, 4), -- Anna lavora su Erbe Aromatiche Bio
(2, 2), -- Giulia lavora su Erbe Aromatiche Bio
(3, 3), -- Luca lavora su Orto Estivo 2024
(4, 2), -- Giulia lavora su Legumi Autunnali
(4, 3); -- Luca lavora su Legumi Autunnali

-- ==============================================================================================
-- COLTIVAZIONI (distribuire colture tra i progetti)
-- ==============================================================================================

-- Progetto 1: Orto Primavera 2025
INSERT INTO coltivazione (note_tecniche, stato, stato_salute, data_inizio, id_progetto, id_coltura) VALUES
('Piantare in pieno sole, irrigazione regolare', 'ATTIVA', 'BUONO', '2025-03-05', 1, 5), -- Pomodoro San Marzano
('Coltivazione in aiuole rialzate', 'ATTIVA', 'OTTIMO', '2025-03-10', 1, 9), -- Lattuga Romana
('Semina diretta in file distanziate', 'ATTIVA', 'BUONO', '2025-03-08', 1, 10); -- Rucola

-- Progetto 2: Erbe Aromatiche Bio
INSERT INTO coltivazione (note_tecniche, stato, stato_salute, data_inizio, id_progetto, id_coltura) VALUES
('Posizione soleggiata, terreno drenante', 'ATTIVA', 'OTTIMO', '2025-02-20', 2, 1), -- Basilico
('Irrigazione moderata, potatura regolare', 'ATTIVA', 'BUONO', '2025-02-20', 2, 2), -- Rosmarino
('Controllare espansione, cresce velocemente', 'IN_RACCOLTA', 'OTTIMO', '2025-02-18', 2, 3), -- Menta
('Semina scaglionata per raccolta continua', 'ATTIVA', 'BUONO', 2, 4); -- Prezzemolo

-- Progetto 3: Orto Estivo 2024 (CONCLUSO)
INSERT INTO coltivazione (note_tecniche, stato, stato_salute, data_inizio, data_fine, id_progetto, id_coltura) VALUES
('Raccolta completata a settembre', 'CONCLUSA', 'BUONO', '2024-06-05', '2024-09-30', 3, 6), -- Zucchina Romanesca
('Ottima produzione estiva', 'CONCLUSA', 'OTTIMO', '2024-06-10', '2024-10-10', 3, 7), -- Peperone Quadrato
('Maturazione tardiva, raccolto abbondante', 'CONCLUSA', 'BUONO', '2024-06-08', '2024-10-05', 3, 8); -- Melanzana Violetta

-- Progetto 4: Legumi Autunnali
INSERT INTO coltivazione (note_tecniche, stato, stato_salute, data_inizio, id_progetto, id_coltura) VALUES
('Varietà rampicante, necessita supporti', 'ATTIVA', 'BUONO', '2025-09-05', 4, 12), -- Fagiolino
('Semina autunnale, resistente al freddo', 'ATTIVA', 'OTTIMO', '2025-09-10', 4, 13); -- Pisello

-- ==============================================================================================
-- ATTIVITÀ (varie attività per le coltivazioni)
-- ==============================================================================================

-- Attività per Coltivazione 1 (Pomodoro San Marzano - Progetto 1)
INSERT INTO attivita (nome, stato, data_pianificazione, data_inizio, data_scadenza, note_tecniche, id_coltivazione, id_coltivatore) VALUES
('Semina Pomodori', 'COMPLETATA', '2025-02-28', '2025-03-05', '2025-03-07', 'Semenzaio protetto con temperatura 20-25°C', 1, 2),
('Irrigazione Pomodori', 'COMPLETATA', '2025-03-10', '2025-03-12', '2025-03-14', 'Irrigazione a goccia programmata', 1, 2),
('Concimazione Pomodori', 'IN_CORSO', '2025-03-15', '2025-03-18', '2025-03-20', 'Concime organico ricco di azoto', 1, 3),
('Tutoraggio Pomodori', 'PIANIFICATA', '2025-04-01', NULL, '2025-04-05', 'Installare pali e legare piante', 1, 2);

-- Attività per Coltivazione 2 (Lattuga Romana - Progetto 1)
INSERT INTO attivita (nome, stato, data_pianificazione, data_inizio, note_tecniche, id_coltivazione, id_coltivatore) VALUES
('Semina Lattuga', 'COMPLETATA', '2025-03-08', '2025-03-10', 'Semina diretta in file distanziate 25cm', 2, 3),
('Irrigazione Lattuga', 'COMPLETATA', '2025-03-15', '2025-03-16', 'Irrigazione leggera quotidiana', 2, 3),
('Diradamento Lattuga', 'PIANIFICATA', '2025-03-25', NULL, 'Diradare piantine lasciando 15cm tra esse', 2, 2);

-- Attività per Coltivazione 3 (Rucola - Progetto 1)
INSERT INTO attivita (nome, stato, data_pianificazione, data_inizio, note_tecniche, id_coltivazione, id_coltivatore) VALUES
('Semina Rucola', 'COMPLETATA', '2025-03-06', '2025-03-08', 'Semina a spaglio in aiuole preparate', 3, 2),
('Raccolta Rucola Prima', 'PIANIFICATA', '2025-04-08', NULL, 'Prima raccolta a taglio dopo 30 giorni', 3, 3);

-- Attività per Coltivazione 4 (Basilico - Progetto 2)
INSERT INTO attivita (nome, stato, data_pianificazione, data_inizio, note_tecniche, id_coltivazione, id_coltivatore) VALUES
('Semina Basilico', 'COMPLETATA', '2025-02-18', '2025-02-20', 'Semenzaio con terriccio leggero', 4, 4),
('Trapianto Basilico', 'COMPLETATA', '2025-03-05', '2025-03-08', 'Trapianto in pieno campo', 4, 2),
('Irrigazione Basilico', 'IN_CORSO', '2025-03-10', '2025-03-12', 'Irrigazione regolare mattutina', 4, 4);

-- Attività per Coltivazione 5 (Rosmarino - Progetto 2)
INSERT INTO attivita (nome, stato, data_pianificazione, data_inizio, note_tecniche, id_coltivazione, id_coltivatore) VALUES
('Trapianto Rosmarino', 'COMPLETATA', '2025-02-18', '2025-02-20', 'Talee radicate trapiantate', 5, 2),
('Potatura Rosmarino', 'PIANIFICATA', '2025-04-15', NULL, 'Potatura di formazione', 5, 4);

-- Attività per Coltivazione 6 (Menta - Progetto 2)
INSERT INTO attivita (nome, stato, data_pianificazione, data_inizio, data_fine, note_tecniche, id_coltivazione, id_coltivatore) VALUES
('Trapianto Menta', 'COMPLETATA', '2025-02-16', '2025-02-18', '2025-02-18', 'Divisione cespi e trapianto', 6, 4),
('Raccolta Menta', 'COMPLETATA', '2025-03-10', '2025-03-12', '2025-03-12', 'Prima raccolta foglie fresche', 6, 2),
('Contenimento Menta', 'PIANIFICATA', '2025-04-01', NULL, 'Limitare espansione radicale', 6, 4);

-- Attività per Coltivazione 7 (Prezzemolo - Progetto 2)
INSERT INTO attivita (nome, stato, data_pianificazione, data_inizio, note_tecniche, id_coltivazione, id_coltivatore) VALUES
('Semina Prezzemolo', 'COMPLETATA', '2025-02-20', '2025-02-22', 'Semina in file, germinazione lenta', 7, 2),
('Irrigazione Prezzemolo', 'IN_CORSO', '2025-03-01', '2025-03-03', 'Mantenere terreno umido', 7, 4);

-- Attività per Coltivazioni Concluse (Progetto 3)
INSERT INTO attivita (nome, stato, data_pianificazione, data_inizio, data_fine, note_tecniche, id_coltivazione, id_coltivatore) VALUES
('Semina Zucchine', 'COMPLETATA', '2024-05-28', '2024-06-05', '2024-06-05', 'Semina diretta a postarelle', 8, 3),
('Raccolta Zucchine', 'COMPLETATA', '2024-07-15', '2024-07-20', '2024-09-25', 'Raccolta scalare tri-settimanale', 8, 3),
('Trapianto Peperoni', 'COMPLETATA', '2024-06-08', '2024-06-10', '2024-06-10', 'Piantine da vivaio', 9, 3),
('Raccolta Peperoni', 'COMPLETATA', '2024-08-20', '2024-08-25', '2024-10-08', 'Raccolta a maturazione completa', 9, 3);

-- ==============================================================================================
-- ATTIVITÀ SPECIALIZZATE (semina, irrigazione, concimazione, trattamento, raccolta)
-- ==============================================================================================

-- Semina
INSERT INTO semina (id, quantita_sementi, profondita_semina_cm) VALUES
(1, 50, 0.50), -- Semina Pomodori
(6, 200, 1.00), -- Semina Lattuga
(8, 300, 0.30), -- Semina Rucola
(9, 100, 0.50), -- Semina Basilico
(16, 150, 1.50), -- Semina Prezzemolo
(18, 80, 2.00), -- Semina Zucchine
(22, 120, 1.00); -- Semina Prezzemolo

-- Irrigazione
INSERT INTO irrigazione (id, metodo, volume_acqua_l) VALUES
(2, 'GOCCIA', 50.00), -- Irrigazione Pomodori
(7, 'MANUALE', 30.00), -- Irrigazione Lattuga
(11, 'GOCCIA', 25.00), -- Irrigazione Basilico
(17, 'MANUALE', 20.00); -- Irrigazione Prezzemolo

-- Concimazione
INSERT INTO concimazione (id, tipo_concime, quantita_kg, metodo_applicazione) VALUES
(3, 'ORGANICO', 5.00, 'Distribuzione superficiale e interramento leggero');

-- Raccolta
INSERT INTO raccolta (id, quantita_prevista_kg, quantita_effettiva_kg) VALUES
(10, 15.00, 18.50), -- Raccolta Rucola Prima
(13, 8.00, 9.20), -- Raccolta Menta
(19, 45.00, 52.30), -- Raccolta Zucchine
(21, 35.00, 38.70); -- Raccolta Peperoni

-- Trattamento
INSERT INTO trattamento (id, nome_prodotto, tempo_carenza, diluzione_dose) VALUES
(4, 'Estratto di Ortica', 0, '100ml/10L acqua');