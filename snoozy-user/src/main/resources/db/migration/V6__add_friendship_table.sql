CREATE TABLE friendships(
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL,
    status VARCHAR,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);