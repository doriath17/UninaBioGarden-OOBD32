
-- inserimento catalogo di 10 colture
INSERT INTO coltura (nome_comune, tempo_maturazione, caratteristiche) VALUES
('Pomodoro', 60, 'Pianta annuale, frutto rosso e succoso'),
('Zucchina', 50, 'Pianta annuale, frutto verde e allungato'),
('Lattuga', 30, 'Pianta annuale, foglie verdi e croccanti'),
('Carota', 70, 'Pianta biennale, radice arancione e dolce'),
('Peperone', 80, 'Pianta annuale, frutto colorato e croccante'),
('Melanzana', 90, 'Pianta annuale, frutto viola e carnoso'),
('Cavolo', 85, 'Pianta biennale, foglie verdi e compatte'),
('Fagiolo', 60, 'Pianta annuale, legume ricco di proteine'),
('Spinacio', 40, 'Pianta annuale, foglie verdi e ricche di ferro'),
('Ravanello', 25, 'Pianta annuale, radice rossa e piccante');

-- inserimento di 3 proprietari
INSERT INTO utente (username, password, email, nome, cognome, b_day, codice_fiscale, tipo) VALUES
('mario_rossi', 'password123', 'mario.rossi@example.com', 'Mario', 'Rossi', '1980-05-15', 'RSSMRA80E15F839X', 'PROPRIETARIO'),
('alessandro_f', 'password123', 'alessandro.f@example.com', 'Alessandro', 'Ferri', '1985-07-20', 'FRRALN85L20F839Y', 'PROPRIETARIO'),
('biaggio_b', 'password123', 'biaggio.b@example.com', 'Biaggio', 'Bianchi', '1990-09-10', 'BNCGBI90P50F839Z', 'PROPRIETARIO');

-- inserimento di 5 coltivatori
INSERT INTO utente (username, password, email, nome, cognome, b_day, codice_fiscale, tipo) VALUES
('giulia_v', 'password123', 'giulia.v@example.com', 'Giulia', 'Verdi', '1992-08-22', 'VRDGLI92M62F839Y', 'COLTIVATORE'),
('luca_n', 'password123', 'luca.n@example.com', 'Luca', 'Neri', '1988-03-10', 'NRILCU88C10F839Z', 'COLTIVATORE'),
('francesca_s', 'password123', 'francesca.s@example.com', 'Francesca', 'Sarti', '1991-12-05', 'SRTFNC91T45F839X', 'COLTIVATORE'),
('andrea_m', 'password123', 'andrea.m@example.com', 'Andrea', 'Martini', '1993-04-15', 'MRTNDR93D15F839Y', 'COLTIVATORE'),
('elena_c', 'password123', 'elena.c@example.com', 'Elena', 'Conti', '1994-06-25', 'CNTELN94H65F839Z', 'COLTIVATORE');

-- inserimento di un orto per ogni proprietario
INSERT INTO orto (nome_orto, citta, cap, via, civico, id_proprietario) VALUES
('Orto di Mario', 'Roma', '00100', 'Via Roma', '1', 1),
('Orto di Alessandro', 'Milano', '20100', 'Via Milano', '2', 2),
('Orto di Biaggio', 'Napoli', '80100', 'Via Napoli', '3', 3);

-- inserimento di un lotto per ogni proprietario
INSERT INTO lotto (codice_lotto, estensione_mq, tipologia_terreno, id_orto, id_proprietario) VALUES
('LOTTO1', 50, 'ARGILLOSO', 1, 1),
('LOTTO2', 75, 'SABBIOSO', 2, 2),
('LOTTO3', 60, 'PIETROSO', 3, 3);

-- inserimento di un progetto per il proprietario 1 (Mario)
INSERT INTO progetto (nome, descrizione, id_proprietario, id_lotto) VALUES
('Progetto Orto Sostenibile', 'Un progetto per promuovere pratiche agricole sostenibili nel nostro orto.', 1, 1);

INSERT INTO lavora_per (id_coltivatore, id_progetto) VALUES
(4, 1), -- Andrea lavora al progetto di Mario
(5, 1); -- Elena lavora al progetto di Mario

INSERT INTO coltivazione (stato, stato_salute, note_tecniche, id_coltura, id_progetto) VALUES
('ATTIVA', 'OTTIMO', 'Coltivazione Pomodori', 1, 1), -- Pomodoro
('ATTIVA', 'STABILE', 'Coltivazione Zucchine', 2, 1), -- Zucchina
('ATTIVA', 'OTTIMO', 'Coltivazione Lattuga', 3, 1); -- Lattuga

INSERT INTO attivita (nome, note_tecniche, id_coltivazione, id_coltivatore) VALUES
('Irrigazione', 'Irrigare i Pomodori', 1, 4), -- Attività per Pomodoro
('Concimazione', 'Concimare i Pomodori', 1, 5), -- Attività per Pomodoro
('Raccolta', 'Raccogliere quando i frutti sono maturi', 1, 4), -- Attività per Pomodoro
('Irrigazione', 'Irrigare le Zucchine', 2, 5), -- Attività per Zucchina
('Controllo parassiti', 'Controllare settimanalmente la presenza di parassiti', 2, 4), -- Attività per Zucchina
('Raccolta', 'Raccogliere quando i frutti sono maturi', 2, 5), -- Attività per Zucchina
('Irrigazione', 'Irrigare le Lattughe', 3, 4), -- Attività per Lattuga
('Concimazione', 'Concimare le Lattughe', 3, 5), -- Attività per Lattuga
('Raccolta', 'Raccogliere quando le foglie sono grandi e croccanti', 3, 4); -- Attività per Lattuga