-- ==============================================================================================
-- Dataset 2 - Dati estesi per la demo dell'applicazione UninaBioGarden
-- ==============================================================================================
--
-- CREDENZIALI DI ACCESSO
-- username       | password    | ruolo
-- ---------------+-------------+------------------------------------------------------------
-- mario_rossi    | password123 | PROPRIETARIO - LOTTO1 (3 stagioni concluse + 1 attiva)
--                |             | + LOTTO1B libero per demo creazione progetto
-- alessandro_f   | password123 | PROPRIETARIO - 1 progetto attivo (peperoni in raccolta)
-- biaggio_b      | password123 | PROPRIETARIO - 1 progetto attivo (cavolo + melanzane)
-- giulia_v       | password123 | COLTIVATORE  - progetti 1,2,3 (Mario) e 4
-- luca_n         | password123 | COLTIVATORE  - progetti 1,2,3 (Mario) e 4
-- francesca_s    | password123 | COLTIVATORE  - progetto 5 (Alessandro) e 6 (Biaggio)
-- andrea_m       | password123 | COLTIVATORE  - progetto 5 (Alessandro)
-- elena_c        | password123 | COLTIVATORE  - progetto 6 (Biaggio)
--
-- REPORT INTERESSANTE: mario_rossi -> LOTTO1
--   Il report mostra 5 colture con min/avg/max differenti grazie a 3 stagioni concluse:
--   Pomodoro  3 raccolte: 7.5 / 8.80 / 9.20 kg  -> min=7.5  avg~8.50 max=9.2
--   Zucchina  2 raccolte: 11.0 / 14.0      kg  -> min=11   avg=12.5  max=14
--   Lattuga   2 raccolte:  3.5 /  4.80     kg  -> min=3.5  avg~4.15  max=4.8
--   Carota    1 raccolta:  6.0              kg  -> min=avg=max=6.0
--   Spinacio  1 raccolta:  5.2              kg  -> min=avg=max=5.2
--
-- RIEPILOGO PROGETTI
-- ID | Nome                      | Proprietario | Lotto   | Stato
-- ---+---------------------------+--------------+---------+----------
--  1 | Stagione Primavera 2024   | mario_rossi  | LOTTO1  | CONCLUSO
--  2 | Stagione Estate 2024      | mario_rossi  | LOTTO1  | CONCLUSO
--  3 | Stagione Autunno 2024     | mario_rossi  | LOTTO1  | CONCLUSO
--  4 | Stagione Primavera 2026   | mario_rossi  | LOTTO1  | ATTIVO
--  5 | Orto Biologico Alessandro | alessandro_f | LOTTO2  | ATTIVO
--  6 | Sperimentazione Biaggio   | biaggio_b    | LOTTO3  | ATTIVO
-- ==============================================================================================


-- ==============================================================================================
-- PARTE 1 - Catalogo colture (10 colture)
-- ==============================================================================================

INSERT INTO coltura (nome_comune, tempo_maturazione, caratteristiche) VALUES
('Pomodoro',  60, 'Pianta annuale, frutto rosso e succoso'),
('Zucchina',  50, 'Pianta annuale, frutto verde e allungato'),
('Lattuga',   30, 'Pianta annuale, foglie verdi e croccanti'),
('Carota',    70, 'Pianta biennale, radice arancione e dolce'),
('Peperone',  80, 'Pianta annuale, frutto colorato e croccante'),
('Melanzana', 90, 'Pianta annuale, frutto viola e carnoso'),
('Cavolo',    85, 'Pianta biennale, foglie verdi e compatte'),
('Fagiolo',   60, 'Pianta annuale, legume ricco di proteine'),
('Spinacio',  40, 'Pianta annuale, foglie verdi e ricche di ferro'),
('Ravanello', 25, 'Pianta annuale, radice rossa e piccante');


-- ==============================================================================================
-- PARTE 2 - Utenti: 3 proprietari (id 1-3) + 5 coltivatori (id 4-8)
-- ==============================================================================================

INSERT INTO utente (username, password, email, nome, cognome, b_day, codice_fiscale, tipo) VALUES
('mario_rossi',  'password123', 'mario.rossi@example.com',   'Mario',     'Rossi',   '1980-05-15', 'RSSMRA80E15F839X', 'PROPRIETARIO'),
('alessandro_f', 'password123', 'alessandro.f@example.com',  'Alessandro','Ferri',   '1985-07-20', 'FRRALN85L20F839Y', 'PROPRIETARIO'),
('biaggio_b',    'password123', 'biaggio.b@example.com',     'Biaggio',   'Bianchi', '1990-09-10', 'BNCGBI90P50F839Z', 'PROPRIETARIO');

INSERT INTO utente (username, password, email, nome, cognome, b_day, codice_fiscale, tipo) VALUES
('giulia_v',    'password123', 'giulia.v@example.com',    'Giulia',    'Verdi',   '1992-08-22', 'VRDGLI92M62F839Y', 'COLTIVATORE'),
('luca_n',      'password123', 'luca.n@example.com',      'Luca',      'Neri',    '1988-03-10', 'NRILCU88C10F839Z', 'COLTIVATORE'),
('francesca_s', 'password123', 'francesca.s@example.com', 'Francesca', 'Sarti',   '1991-12-05', 'SRTFNC91T45F839X', 'COLTIVATORE'),
('andrea_m',    'password123', 'andrea.m@example.com',    'Andrea',    'Martini', '1993-04-15', 'MRTNDR93D15F839Y', 'COLTIVATORE'),
('elena_c',     'password123', 'elena.c@example.com',     'Elena',     'Conti',   '1994-06-25', 'CNTELN94H65F839Z', 'COLTIVATORE');


-- ==============================================================================================
-- PARTE 3 - Orti (uno per proprietario)
-- ==============================================================================================

INSERT INTO orto (nome_orto, citta, cap, via, civico, id_proprietario) VALUES
('Orto di Mario',      'Roma',   '00100', 'Via Roma',   '1', 1),
('Orto di Alessandro', 'Milano', '20100', 'Via Milano', '2', 2),
('Orto di Biaggio',    'Napoli', '80100', 'Via Napoli', '3', 3);


-- ==============================================================================================
-- PARTE 4 - Lotti
--   LOTTO1  (id=1): Mario - usato da tutti i 4 progetti di Mario (genera dati storici report)
--   LOTTO2  (id=2): Alessandro - progetto 5
--   LOTTO3  (id=3): Biaggio - progetto 6
--   LOTTO1B (id=4): Mario - libero, utile per la demo di creazione nuovo progetto
-- ==============================================================================================

INSERT INTO lotto (codice_lotto, estensione_mq, tipologia_terreno, id_orto, id_proprietario) VALUES
('LOTTO1',  50,  'ARGILLOSO',     1, 1),
('LOTTO2',  75,  'SABBIOSO',      2, 2),
('LOTTO3',  60,  'ARGILLOSO',     3, 3),
('LOTTO1B', 100, 'MEDIO_IMPASTO', 1, 1);


-- ==============================================================================================
-- ============================================================================
--  MARIO - LOTTO1 - STAGIONE PRIMAVERA 2024 (CONCLUSO)
--  Colture: Pomodoro(7.5 kg), Zucchina(11.0 kg), Lattuga(3.5 kg)
-- ============================================================================
-- ==============================================================================================

INSERT INTO progetto (nome, descrizione, id_proprietario, id_lotto) VALUES
('Stagione Primavera 2024',
 'Prima stagione di coltivazione primaverile con pomodori, zucchine e lattughe.',
 1, 1);  -- progetto id=1

INSERT INTO lavora_per (id_coltivatore, id_progetto) VALUES
(4, 1),  -- giulia_v
(5, 1);  -- luca_n

-- coltivazioni
INSERT INTO coltivazione (stato_salute, note_tecniche, id_coltura, id_progetto) VALUES
('OTTIMO',  'Pomodori primavera 2024', 1, 1),  -- coltivazione id=1
('OTTIMO',  'Zucchine primavera 2024', 2, 1),  -- coltivazione id=2
('OTTIMO',  'Lattughe primavera 2024', 3, 1);  -- coltivazione id=3

-- attivita (una semina + una raccolta per coltivazione)
INSERT INTO attivita (nome, note_tecniche, tipo, id_coltivazione, id_coltivatore) VALUES
('Semina',   'Semina Pomodori',   'SEMINA',   1, 4),  -- id=1
('Raccolta', 'Raccolta Pomodori', 'RACCOLTA', 1, 5),  -- id=2
('Semina',   'Semina Zucchine',   'SEMINA',   2, 4),  -- id=3
('Raccolta', 'Raccolta Zucchine', 'RACCOLTA', 2, 5),  -- id=4
('Semina',   'Semina Lattughe',   'SEMINA',   3, 5),  -- id=5
('Raccolta', 'Raccolta Lattughe', 'RACCOLTA', 3, 4);  -- id=6

-- sottotipi
INSERT INTO semina   (id, quantita_sementi, profondita_semina_cm)         VALUES (1, 20, 2.0);
INSERT INTO semina   (id, quantita_sementi, profondita_semina_cm)         VALUES (3, 15, 1.5);
INSERT INTO semina   (id, quantita_sementi, profondita_semina_cm)         VALUES (5, 30, 1.0);
INSERT INTO raccolta (id, quantita_prevista_kg, quantita_effettiva_kg)    VALUES (2, 10.0,  7.5);
INSERT INTO raccolta (id, quantita_prevista_kg, quantita_effettiva_kg)    VALUES (4, 12.0, 11.0);
INSERT INTO raccolta (id, quantita_prevista_kg, quantita_effettiva_kg)    VALUES (6,  5.0,  3.5);

-- completamento: semina COMPLETATA, poi raccolta PIANIFICATA -> IN_CORSO -> COMPLETATA
UPDATE attivita SET stato = 'COMPLETATA' WHERE id IN (1, 3, 5);
UPDATE attivita SET stato = 'IN_CORSO'   WHERE id IN (2, 4, 6);
UPDATE attivita SET stato = 'COMPLETATA' WHERE id IN (2, 4, 6);
-- (trigger: coltivazioni 1,2,3 -> CONCLUSA)

UPDATE progetto SET stato = 'CONCLUSO' WHERE id = 1;


-- ==============================================================================================
-- ============================================================================
--  MARIO - LOTTO1 - STAGIONE ESTATE 2024 (CONCLUSO)
--  Colture: Pomodoro(9.2 kg), Zucchina(14.0 kg), Carota(6.0 kg)
-- ============================================================================
-- ==============================================================================================

INSERT INTO progetto (nome, descrizione, id_proprietario, id_lotto) VALUES
('Stagione Estate 2024',
 'Stagione estiva: coltivazione intensiva di pomodori, zucchine e carote.',
 1, 1);  -- progetto id=2

INSERT INTO lavora_per (id_coltivatore, id_progetto) VALUES
(4, 2),  -- giulia_v
(5, 2);  -- luca_n

INSERT INTO coltivazione (stato_salute, note_tecniche, id_coltura, id_progetto) VALUES
('OTTIMO',  'Pomodori estate 2024', 1, 2),  -- coltivazione id=4
('OTTIMO',  'Zucchine estate 2024', 2, 2),  -- coltivazione id=5
('OTTIMO',  'Carote estate 2024',   4, 2);  -- coltivazione id=6

INSERT INTO attivita (nome, note_tecniche, tipo, id_coltivazione, id_coltivatore) VALUES
('Semina',   'Semina Pomodori',   'SEMINA',   4, 4),  -- id=7
('Raccolta', 'Raccolta Pomodori', 'RACCOLTA', 4, 5),  -- id=8
('Semina',   'Semina Zucchine',   'SEMINA',   5, 5),  -- id=9
('Raccolta', 'Raccolta Zucchine', 'RACCOLTA', 5, 4),  -- id=10
('Semina',   'Semina Carote',     'SEMINA',   6, 4),  -- id=11
('Raccolta', 'Raccolta Carote',   'RACCOLTA', 6, 5);  -- id=12

INSERT INTO semina   (id, quantita_sementi, profondita_semina_cm)      VALUES (7,  25, 2.0);
INSERT INTO semina   (id, quantita_sementi, profondita_semina_cm)      VALUES (9,  20, 1.5);
INSERT INTO semina   (id, quantita_sementi, profondita_semina_cm)      VALUES (11, 35, 1.5);
INSERT INTO raccolta (id, quantita_prevista_kg, quantita_effettiva_kg) VALUES (8,  10.0,  9.2);
INSERT INTO raccolta (id, quantita_prevista_kg, quantita_effettiva_kg) VALUES (10, 16.0, 14.0);
INSERT INTO raccolta (id, quantita_prevista_kg, quantita_effettiva_kg) VALUES (12,  7.0,  6.0);

UPDATE attivita SET stato = 'COMPLETATA' WHERE id IN (7, 9, 11);
UPDATE attivita SET stato = 'IN_CORSO'   WHERE id IN (8, 10, 12);
UPDATE attivita SET stato = 'COMPLETATA' WHERE id IN (8, 10, 12);

UPDATE progetto SET stato = 'CONCLUSO' WHERE id = 2;


-- ==============================================================================================
-- ============================================================================
--  MARIO - LOTTO1 - STAGIONE AUTUNNO 2024 (CONCLUSO)
--  Colture: Pomodoro(8.8 kg), Lattuga(4.8 kg), Spinacio(5.2 kg)
-- ============================================================================
-- ==============================================================================================

INSERT INTO progetto (nome, descrizione, id_proprietario, id_lotto) VALUES
('Stagione Autunno 2024',
 'Ciclo autunnale: ultima produzione di pomodori, lattughe tardive e spinaci.',
 1, 1);  -- progetto id=3

INSERT INTO lavora_per (id_coltivatore, id_progetto) VALUES
(4, 3),  -- giulia_v
(5, 3);  -- luca_n

INSERT INTO coltivazione (stato_salute, note_tecniche, id_coltura, id_progetto) VALUES
('OTTIMO', 'Pomodori autunno 2024', 1, 3),  -- coltivazione id=7
('OTTIMO', 'Lattughe autunno 2024', 3, 3),  -- coltivazione id=8
('OTTIMO', 'Spinaci autunno 2024',  9, 3);  -- coltivazione id=9

INSERT INTO attivita (nome, note_tecniche, tipo, id_coltivazione, id_coltivatore) VALUES
('Semina',   'Semina Pomodori',   'SEMINA',   7, 5),  -- id=13
('Raccolta', 'Raccolta Pomodori', 'RACCOLTA', 7, 4),  -- id=14
('Semina',   'Semina Lattughe',   'SEMINA',   8, 4),  -- id=15
('Raccolta', 'Raccolta Lattughe', 'RACCOLTA', 8, 5),  -- id=16
('Semina',   'Semina Spinaci',    'SEMINA',   9, 5),  -- id=17
('Raccolta', 'Raccolta Spinaci',  'RACCOLTA', 9, 4);  -- id=18

INSERT INTO semina   (id, quantita_sementi, profondita_semina_cm)      VALUES (13, 20, 2.0);
INSERT INTO semina   (id, quantita_sementi, profondita_semina_cm)      VALUES (15, 30, 1.0);
INSERT INTO semina   (id, quantita_sementi, profondita_semina_cm)      VALUES (17, 50, 1.0);
INSERT INTO raccolta (id, quantita_prevista_kg, quantita_effettiva_kg) VALUES (14, 10.0, 8.8);
INSERT INTO raccolta (id, quantita_prevista_kg, quantita_effettiva_kg) VALUES (16,  5.0, 4.8);
INSERT INTO raccolta (id, quantita_prevista_kg, quantita_effettiva_kg) VALUES (18,  6.0, 5.2);

UPDATE attivita SET stato = 'COMPLETATA' WHERE id IN (13, 15, 17);
UPDATE attivita SET stato = 'IN_CORSO'   WHERE id IN (14, 16, 18);
UPDATE attivita SET stato = 'COMPLETATA' WHERE id IN (14, 16, 18);

UPDATE progetto SET stato = 'CONCLUSO' WHERE id = 3;


-- ==============================================================================================
-- ============================================================================
--  MARIO - LOTTO1 - STAGIONE PRIMAVERA 2026 (ATTIVO - progetto corrente di Mario)
--  Colture: Pomodoro (in lavorazione), Zucchina (in lavorazione)
-- ============================================================================
-- ==============================================================================================

INSERT INTO progetto (nome, descrizione, id_proprietario, id_lotto) VALUES
('Stagione Primavera 2026',
 'Nuova stagione primaverile: pomodori e zucchine con tecniche migliorate.',
 1, 1);  -- progetto id=4

INSERT INTO lavora_per (id_coltivatore, id_progetto) VALUES
(4, 4),  -- giulia_v
(5, 4);  -- luca_n

INSERT INTO coltivazione (stato_salute, note_tecniche, id_coltura, id_progetto) VALUES
('OTTIMO',  'Pomodori primavera 2026 - ottima crescita', 1, 4),  -- coltivazione id=10
('STABILE', 'Zucchine primavera 2026',                   2, 4);  -- coltivazione id=11

INSERT INTO attivita (nome, note_tecniche, tipo, id_coltivazione, id_coltivatore) VALUES
('Semina',        'Semina Pomodori a 2 cm',                      'SEMINA',        10, 4),  -- id=19
('Irrigazione',   'Irrigazione con sistema a goccia',            'IRRIGAZIONE',   10, 5),  -- id=20
('Concimazione',  'Concimazione organica in copertura',          'CONCIMAZIONE',  10, 4),  -- id=21
('Raccolta',      'Raccogliere quando i frutti sono maturi',      'RACCOLTA',      10, 5),  -- id=22
('Semina',        'Semina Zucchine in semenzaio',                'SEMINA',        11, 5),  -- id=23
('Trattamento',   'Controllo preventivo afidi',                  'TRATTAMENTO',   11, 4),  -- id=24
('Raccolta',      'Raccogliere i frutti a maturazione completa',  'RACCOLTA',      11, 5);  -- id=25

INSERT INTO semina       (id, quantita_sementi, profondita_semina_cm)         VALUES (19, 20, 2.0);
INSERT INTO semina       (id, quantita_sementi, profondita_semina_cm)         VALUES (23, 18, 1.5);
INSERT INTO irrigazione  (id, metodo, volume_acqua_l)                         VALUES (20, 'GOCCIA', 3.0);
INSERT INTO concimazione (id, tipo_concime, quantita_kg)                      VALUES (21, 'ORGANICO', 5.0);
INSERT INTO trattamento  (id, nome_prodotto, tempo_carenza)                   VALUES (24, 'Verderame Bio', 7);
INSERT INTO raccolta     (id, quantita_prevista_kg, quantita_effettiva_kg)    VALUES (22, 11.0, NULL);
INSERT INTO raccolta     (id, quantita_prevista_kg, quantita_effettiva_kg)    VALUES (25, 14.0, NULL);

-- semina completata, irrigazione in corso, concimazione e raccolta ancora pianificate
UPDATE attivita SET stato = 'COMPLETATA' WHERE id IN (19, 23);
UPDATE attivita SET stato = 'IN_CORSO'   WHERE id = 20;


-- ==============================================================================================
-- ============================================================================
--  ALESSANDRO - LOTTO2 - PROGETTO ATTIVO
--  Peperoni: semina/concimazione completate -> raccolta IN_CORSO (colt IN_RACCOLTA)
--  Carote: semina completata, irrigazione IN_CORSO
-- ============================================================================
-- ==============================================================================================

INSERT INTO progetto (nome, descrizione, id_proprietario, id_lotto) VALUES
('Orto Biologico Alessandro',
 'Coltivazione biologica a basso impatto: peperoni e carote.',
 2, 2);  -- progetto id=5

INSERT INTO lavora_per (id_coltivatore, id_progetto) VALUES
(6, 5),  -- francesca_s
(7, 5);  -- andrea_m

INSERT INTO coltivazione (stato_salute, note_tecniche, id_coltura, id_progetto) VALUES
('STABILE',   'Peperoni biologici',            5, 5),  -- coltivazione id=12
('OTTIMO',    'Carote - crescita regolare',     4, 5);  -- coltivazione id=13

INSERT INTO attivita (nome, note_tecniche, tipo, id_coltivazione, id_coltivatore) VALUES
('Semina',       'Semina Peperoni in semenzaio',       'SEMINA',       12, 6),  -- id=26
('Concimazione', 'Concimazione fogliare azoto org.',   'CONCIMAZIONE', 12, 7),  -- id=27
('Raccolta',     'Raccolta peperoni a piena maturi.',  'RACCOLTA',     12, 6),  -- id=28
('Semina',       'Semina Carote a 1.5 cm',             'SEMINA',       13, 7),  -- id=29
('Irrigazione',  'Irrigazione con sistema a goccia',   'IRRIGAZIONE',  13, 6),  -- id=30
('Raccolta',     'Raccolta Carote a piena maturaz.',   'RACCOLTA',     13, 7);  -- id=31

INSERT INTO semina       (id, quantita_sementi, profondita_semina_cm)      VALUES (26, 15, 1.0);
INSERT INTO concimazione (id, tipo_concime, quantita_kg)                   VALUES (27, 'ORGANICO', 3.5);
INSERT INTO raccolta     (id, quantita_prevista_kg, quantita_effettiva_kg) VALUES (28, 8.0, NULL);
INSERT INTO semina       (id, quantita_sementi, profondita_semina_cm)      VALUES (29, 35, 1.5);
INSERT INTO irrigazione  (id, metodo, volume_acqua_l)                      VALUES (30, 'GOCCIA', 5.0);
INSERT INTO raccolta     (id, quantita_prevista_kg, quantita_effettiva_kg) VALUES (31, 9.0, NULL);

-- peperoni: semina+concimazione -> COMPLETATA, poi raccolta -> IN_CORSO (trigger: colt 12 -> IN_RACCOLTA)
UPDATE attivita SET stato = 'COMPLETATA' WHERE id IN (26, 27);
UPDATE attivita SET stato = 'IN_CORSO'   WHERE id = 28;
-- carote: semina -> COMPLETATA, irrigazione -> IN_CORSO
UPDATE attivita SET stato = 'COMPLETATA' WHERE id = 29;
UPDATE attivita SET stato = 'IN_CORSO'   WHERE id = 30;


-- ==============================================================================================
-- ============================================================================
--  BIAGGIO - LOTTO3 - PROGETTO ATTIVO
--  Cavolo: trattamento urgente IN_CORSO (stato_salute SOFFERENTE)
--  Melanzane: semina completata, concimazione pianificata
-- ============================================================================
-- ==============================================================================================

INSERT INTO progetto (nome, descrizione, id_proprietario, id_lotto) VALUES
('Sperimentazione Biaggio',
 'Sperimentazione su terreno argilloso: cavolo e melanzane con monitoraggio intensivo.',
 3, 3);  -- progetto id=6

INSERT INTO lavora_per (id_coltivatore, id_progetto) VALUES
(8, 6),  -- elena_c
(6, 6);  -- francesca_s

INSERT INTO coltivazione (stato_salute, note_tecniche, id_coltura, id_progetto) VALUES
('SOFFERENTE', 'Cavolo - monitorare afidi urgentemente', 7, 6),  -- coltivazione id=14
('STABILE',    'Melanzane - crescita normale',            6, 6);  -- coltivazione id=15

INSERT INTO attivita (nome, note_tecniche, tipo, id_coltivazione, id_coltivatore) VALUES
('Semina',        'Trapianto plantule a 40 cm',              'SEMINA',        14, 8),  -- id=32
('Trattamento',   'Trattamento antiparassitario afidi',      'TRATTAMENTO',   14, 6),  -- id=33
('Raccolta',      'Raccolta teste compatte prima del gelo',  'RACCOLTA',      14, 8),  -- id=34
('Semina',        'Semina melanzane in semenzaio a 25C',     'SEMINA',        15, 8),  -- id=35
('Concimazione',  'Concimazione organica in copertura',      'CONCIMAZIONE',  15, 6),  -- id=36
('Raccolta',      'Raccolta prima della piena maturazione',  'RACCOLTA',      15, 8);  -- id=37

INSERT INTO semina       (id, quantita_sementi, profondita_semina_cm)      VALUES (32, 25, 1.0);
INSERT INTO trattamento  (id, nome_prodotto, tempo_carenza)                VALUES (33, 'Rame Idrossido Bio', 14);
INSERT INTO raccolta     (id, quantita_prevista_kg, quantita_effettiva_kg) VALUES (34, 18.0, NULL);
INSERT INTO semina       (id, quantita_sementi, profondita_semina_cm)      VALUES (35, 10, 2.5);
INSERT INTO concimazione (id, tipo_concime, quantita_kg)                   VALUES (36, 'ORGANICO', 6.0);
INSERT INTO raccolta     (id, quantita_prevista_kg, quantita_effettiva_kg) VALUES (37, 20.0, NULL);

-- cavolo: semina COMPLETATA, trattamento urgente IN_CORSO
UPDATE attivita SET stato = 'COMPLETATA' WHERE id = 32;
UPDATE attivita SET stato = 'IN_CORSO'   WHERE id = 33;
-- melanzane: semina COMPLETATA
UPDATE attivita SET stato = 'COMPLETATA' WHERE id = 35;


-- ==============================================================================================
-- PARTE FINALE - Notifiche
-- VINCOLO: UNIQUE (id_progetto, data_invio) -> timestamp distinti per progetto.
-- ==============================================================================================

-- Progetto 4 (Mario, attivo)
INSERT INTO notifica (nome_evento, urgenza, descrizione, tipo, data_invio, id_progetto) VALUES
('Irrigazione Pomodori',  'MEDIA',  'Irrigare i pomodori domani mattina.',             'NOTIFICA_PROGETTO', '2026-03-15 08:00:00', 4),
('Concimazione Urgente',  'ALTA',   'Concimare prima del prossimo ciclo di crescita.', 'NOTIFICA_PROGETTO', '2026-03-18 09:00:00', 4);

INSERT INTO notifica (nome_evento, urgenza, descrizione, tipo, data_invio, giorni_mancanti, id_progetto, id_attivita) VALUES
('Raccolta Imminente Pomodori', 'ALTA',
 'La raccolta dei pomodori e prevista tra 2 giorni. Completare tutte le attivita precedenti.',
 'NOTIFICA_ATTIVITA_IMMINENTE', '2026-03-20 07:00:00', 2, 4, 22);

-- Progetto 5 (Alessandro)
INSERT INTO notifica (nome_evento, urgenza, descrizione, tipo, data_invio, id_progetto) VALUES
('Infestazione Peperoni', 'CRITICA',
 'Rilevata infestazione di tripidi. Intervenire immediatamente.',
 'NOTIFICA_PROGETTO', '2026-03-10 08:00:00', 5);

-- Progetto 6 (Biaggio)
INSERT INTO notifica (nome_evento, urgenza, descrizione, tipo, data_invio, id_progetto) VALUES
('Trattamento Urgente Cavolo', 'CRITICA',
 'Presenza massiccia di afidi. Trattamento con rame idrossido in corso.',
 'NOTIFICA_PROGETTO', '2026-03-18 07:00:00', 6),
('Irrigazione Melanzane', 'BASSA',
 'Promemoria: irrigazione manuale settimanale delle melanzane.',
 'NOTIFICA_PROGETTO', '2026-03-19 08:00:00', 6);


-- ==============================================================================================
-- Distribuzione notifiche (riceve)
-- ==============================================================================================

-- Notifica 1 (P4, Irrigazione) -> giulia_v(4), luca_n(5)
INSERT INTO riceve (id_notifica, id_coltivatore, is_letta, data_lettura) VALUES
(1, 4, TRUE,  '2026-03-15 09:00:00'),
(1, 5, TRUE,  '2026-03-15 09:30:00');

-- Notifica 2 (P4, Concimazione) -> giulia_v(4), luca_n(5)
INSERT INTO riceve (id_notifica, id_coltivatore, is_letta, data_lettura) VALUES
(2, 4, TRUE,  '2026-03-18 10:00:00'),
(2, 5, FALSE, NULL);

-- Notifica 3 (P4, Raccolta Imminente) -> giulia_v(4), luca_n(5)
INSERT INTO riceve (id_notifica, id_coltivatore, is_letta, data_lettura) VALUES
(3, 4, FALSE, NULL),
(3, 5, FALSE, NULL);

-- Notifica 4 (P5, Infestazione) -> francesca_s(6), andrea_m(7)
INSERT INTO riceve (id_notifica, id_coltivatore, is_letta, data_lettura) VALUES
(4, 6, FALSE, NULL),
(4, 7, FALSE, NULL);

-- Notifica 5 (P6, Trattamento) -> elena_c(8), francesca_s(6)
INSERT INTO riceve (id_notifica, id_coltivatore, is_letta, data_lettura) VALUES
(5, 8, FALSE, NULL),
(5, 6, FALSE, NULL);

-- Notifica 6 (P6, Irrigazione) -> elena_c(8), francesca_s(6)
INSERT INTO riceve (id_notifica, id_coltivatore, is_letta, data_lettura) VALUES
(6, 8, TRUE,  '2026-03-19 09:00:00'),
(6, 6, FALSE, NULL);
