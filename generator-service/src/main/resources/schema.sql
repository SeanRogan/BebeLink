--install extension for uuid auto generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
--create the url object table
CREATE TABLE IF NOT EXISTS url
(
    id           UUID PRIMARY KEY,
    origin       TEXT,
    short_url    VARCHAR(255),
    created_on   TIMESTAMP WITHOUT TIME ZONE,
    last_updated TIMESTAMP WITHOUT TIME ZONE,
    expires      TIMESTAMP WITHOUT TIME ZONE,
    active       boolean
);
--create an index to improve lookups
CREATE INDEX IF NOT EXISTS idx_url_origin_shorturl
    ON url (origin, short_url);
-- create a function to update 'last_update' timestamps when the table is updated
CREATE OR REPLACE FUNCTION update_last_update_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.last_updated = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;
--create a function to deactivate links that have expired
CREATE OR REPLACE FUNCTION update_active_on_expires()
    RETURNS VOID AS
$$
BEGIN
    UPDATE url
    SET active = false
    WHERE expires < CURRENT_TIMESTAMP
      AND active = true;
END;
$$
    LANGUAGE plpgsql;
--create a trigger to activate the update_last_updated function
DROP TRIGGER IF EXISTS update_last_updated ON url;
CREATE TRIGGER update_last_updated
    AFTER UPDATE
    ON url
    FOR EACH ROW
EXECUTE FUNCTION update_last_update_timestamp();
--create a stored procedure to execute the scheduled update function
CREATE OR REPLACE PROCEDURE execute_update_active()
    LANGUAGE plpgsql
AS
$$
BEGIN
    CALL update_active_on_expires();
END
$$;
