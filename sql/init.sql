-- init.sql
\set ON_ERROR_STOP on

\i ./tables/table_utente.sql
\i ./tables/table_orto.sql
\i ./tables/table_lotto.sql
\i ./tables/table_progetto.sql
\i ./tables/table_coltura.sql
\i ./tables/table_coltivazione.sql
\i ./tables/table_attivita.sql
\i ./tables/table_notifica.sql

\i ./constraints/constraints_orto.sql
\i ./constraints/constraints_lotto.sql
\i ./constraints/constraints_attivita.sql