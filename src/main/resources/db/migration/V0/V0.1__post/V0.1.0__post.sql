CREATE TABLE IF NOT EXISTS posts (

    -- PRIMARY FIELDS
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    description TEXT NOT NULL,
    photo_url VARCHAR,
    video_url VARCHAR,
    private BOOLEAN NOT NULL DEFAULT FALSE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE RESTRICT ON UPDATE CASCADE,

    -- AUDIT FIELDS
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- INDEXES
CREATE INDEX idx_posts_active ON posts(id) WHERE deleted = FALSE;
CREATE INDEX idx_posts_user_id ON posts(user_id);