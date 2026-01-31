-- one password to rule them all: password1 --> $2a$12$kQn10Qeyitqp1hHSyfEfsujMHZh/UtgsXUaqm4/n4gXoVftUeT.Om

-- ==============================================================================================
-- Popolamento utenti
--
-- 5 utenti di cui:
--    2 proprietari
--    3 coltivatori
-- ==============================================================================================

INSERT INTO utente (username, password, email, nome, cognome, b_day, codice_fiscale, gender, bio, tipo)
VALUES
('mrossi88', '$2a$12$kQn10Qeyitqp1hHSyfEfsujMHZh/UtgsXUaqm4/n4gXoVftUeT.Om', 'mario.rossi@email.it', 'Mario', 'Rossi', '1988-05-12', 'RSSMRA88E12H501Z', 'M', 'Appassionato di tecnologia e trekking.', 'proprietario'),
('laura_verdi', '$2a$12$kQn10Qeyitqp1hHSyfEfsujMHZh/UtgsXUaqm4/n4gXoVftUeT.Om', 'l.verdi@provider.com', 'Laura', 'Verdi', '1992-11-23', 'VRDLRA92S63F205H', 'F', 'Graphic designer freelance con la passione per i viaggi.', 'proprietario'),
('luca_bianchi', '$2a$12$kQn10Qeyitqp1hHSyfEfsujMHZh/UtgsXUaqm4/n4gXoVftUeT.Om', 'luca.bianchi@webmail.it', 'Luca', 'Bianchi', '1985-02-02', 'BNCLCU85B02L219X', 'M', 'Sviluppatore software e amante della cucina italiana.', 'coltivatore'),
('giulia_neri', '$2a$12$kQn10Qeyitqp1hHSyfEfsujMHZh/UtgsXUaqm4/n4gXoVftUeT.Om', 'giulia.neri@service.org', 'Giulia', 'Neri', '1995-07-30', 'NREGLI95L70G273E', 'F', 'Studentessa di biologia e volontaria ambientale.', 'coltivatore'),
('fra_esposito', '$2a$12$kQn10Qeyitqp1hHSyfEfsujMHZh/UtgsXUaqm4/n4gXoVftUeT.Om', 'francesco.espo@mail.com', 'Francesco', 'Esposito', '1990-09-15', 'SPSFNC90P15F839U', 'M', 'Personal trainer certificato e maratoneta.', 'coltivatore');