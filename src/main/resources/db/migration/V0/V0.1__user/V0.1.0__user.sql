CREATE TABLE IF NOT EXISTS users (

    --PRIMARY FIELDS
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    username VARCHAR NOT NULL UNIQUE,
    description TEXT NOT NULL,
    phone_number VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    profile_picture VARCHAR NOT NULL,

    --AUDIT FIELDS
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()

);

CREATE TRIGGER updated_at_trigger
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE PROCEDURE updated_at_trigger_function();