-- Run as superuser on the target database
BEGIN;
DROP SCHEMA public CASCADE;
CREATE SCHEMA public AUTHORIZATION current_user;
GRANT ALL ON SCHEMA public TO current_user;
GRANT ALL ON SCHEMA public TO public;
COMMIT;
