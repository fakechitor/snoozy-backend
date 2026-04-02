CREATE TABLE group_members(
    group_id BIGINT REFERENCES groups(id),
    user_id BIGINT REFERENCES users(id),
    joined_at TIMESTAMP NOT NULL DEFAULT NOW()
);