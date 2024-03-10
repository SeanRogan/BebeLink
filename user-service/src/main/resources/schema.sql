--install extension for uuid auto generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
--create the url object table
CREATE TABLE IF NOT EXISTS "url"
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    origin    TEXT                NOT NULL,
    short_url VARCHAR(255) UNIQUE NOT NULL,
    expires   TIMESTAMP WITHOUT TIME ZONE,
    active    BOOLEAN             NOT NULL
);

--create the profile table without its foreign key to users, as users is not created yet
CREATE TABLE IF NOT EXISTS "user_profile"
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id     UUID,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    last_update TIMESTAMP WITHOUT TIME ZONE
);

--create the user table, which has a one to one relationship with the user_profile
CREATE TABLE IF NOT EXISTS "users"
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username    VARCHAR(32) NOT NULL,
    email       VARCHAR(64) UNIQUE,
    password    VARCHAR(32) NOT NULL,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    last_update TIMESTAMP WITHOUT TIME ZONE,
    profile_id  UUID,
    role        VARCHAR(14),
    FOREIGN KEY (profile_id) REFERENCES user_profile (id) ON DELETE CASCADE
);

--index the email which is used in queries
CREATE INDEX IF NOT EXISTS idx_users_email ON users (email);
--index the foreign key column
CREATE INDEX IF NOT EXISTS idx_users_profile ON users (profile_id);

--index the foreign key column
CREATE INDEX IF NOT EXISTS idx_users_profile_user_id ON user_profile (user_id);


--url users table associates a url with a user. many to many relationship
CREATE TABLE IF NOT EXISTS "url_users"
(
    id      UUID PRIMARY KEY,
    user_id UUID,
    url_id  UUID,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (url_id) REFERENCES url (id) ON DELETE CASCADE
);
--index the foreign key fields in url_users
CREATE INDEX IF NOT EXISTS idx_url_users_user_id ON url_users (user_id);
CREATE INDEX IF NOT EXISTS idx_url_users_url_id ON url_users (url_id);


-- create function to update timestamp when items are modified
CREATE OR REPLACE FUNCTION update_last_update_timestamp()
    RETURNS TRIGGER AS
'BEGIN
    NEW.last_update = CURRENT_TIMESTAMP;
    RETURN NEW;
END;'
    LANGUAGE plpgsql;
--create a trigger set for the update_last_update_timestamp
--drop existing triggers
DROP TRIGGER IF EXISTS update_last_updated_users ON users;
--create trigger for users table
CREATE TRIGGER update_last_updated_users
    AFTER UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_last_update_timestamp();
--drop existing triggers
DROP TRIGGER IF EXISTS update_last_updated_user_profile ON user_profile;
--create trigger for profile table
CREATE TRIGGER update_last_updated_user_profile
    AFTER UPDATE
    ON user_profile
    FOR EACH ROW
EXECUTE FUNCTION update_last_update_timestamp();
--drop existing triggers
DROP TRIGGER IF EXISTS update_last_updated_url ON url;
--create trigger for url table
CREATE TRIGGER update_last_updated_url
    AFTER UPDATE
    ON url
    FOR EACH ROW
EXECUTE FUNCTION update_last_update_timestamp();


