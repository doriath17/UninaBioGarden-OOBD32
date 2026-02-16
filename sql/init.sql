-- init.sql

-- questo file è usato per inizializzare il database, creando tutte le tabelle e i vincoli necessari
-- utilizza i comandi di psql per eseguire i file SQL che definiscono le tabelle e i vincoli
-- per questo motivo non è portabile perché è pensato appositamente per inizializzare un database PostgreSQL
-- usando psql. Un alternativa sarebbe quella di creare un unico script SQL. 

-- si può usare il comando \i init.sql dalla shell interattiva di psql, lanciandolo dalla cartella sql. 

-- fa fermare l'esecuzione in caso di errori al primo errore
\set ON_ERROR_STOP on

-- inizializza le tabelle
\i ./tables/table_utente.sql
\i ./tables/table_orto.sql
\i ./tables/table_lotto.sql
\i ./tables/table_progetto.sql
\i ./tables/table_coltura.sql
\i ./tables/table_coltivazione.sql
\i ./tables/table_attivita.sql
\i ./tables/table_notifica.sql

-- inizializza i vincoli
\i ./constraints/constraints_orto.sql
\i ./constraints/constraints_lotto.sql
\i ./constraints/constraints_progetto.sql
\i ./constraints/constraints_lavora_per.sql
\i ./constraints/constraints_coltivazione.sql
\i ./constraints/constraints_attivita.sql
\i ./constraints/constraints_attivita_subtypes.sql
\i ./constraints/constraints_notifica.sql