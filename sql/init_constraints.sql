-- init.sql
\set ON_ERROR_STOP on

\i ./constraints/constraints_orto.sql
\i ./constraints/constraints_lotto.sql
\i ./constraints/constraints_attivita.sql
\i ./constraints/constraints_coltivazione.sql
\i ./constraints/constraints_progetto.sql
\i ./constraints/constraints_notifica.sql