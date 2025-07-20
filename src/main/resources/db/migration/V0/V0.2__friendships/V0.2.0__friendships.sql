CREATE TABLE IF NOT EXISTS friendships (
    -- PRIMARY FIELDS
    user_id_1 BIGINT NOT NULL REFERENCES users(id),
    user_id_2 BIGINT NOT NULL REFERENCES users(id),
    PRIMARY KEY (user_id_1, user_id_2)
);

-- INDEXES
CREATE INDEX idx_friendships_user1 ON friendships(user_id_1);
CREATE INDEX idx_friendships_user2 ON friendships(user_id_2);