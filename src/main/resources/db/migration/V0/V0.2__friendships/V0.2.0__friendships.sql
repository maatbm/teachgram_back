CREATE TABLE IF NOT EXISTS friendships (

    -- PRIMARY FIELDS
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id_1 BIGINT NOT NULL REFERENCES users(id),
    user_id_2 BIGINT NOT NULL REFERENCES users(id),

    -- AUDIT FIELDS
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP

);

-- INDEXES
CREATE INDEX idx_friendships_user1 ON friendships(user_id_1);
CREATE INDEX idx_friendships_user2 ON friendships(user_id_2);