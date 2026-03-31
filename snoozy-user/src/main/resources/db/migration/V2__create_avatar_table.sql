CREATE TABLE avatars(
    id BIGSERIAL PRIMARY KEY,
    object_key VARCHAR NOT NULL UNIQUE,
    content_type VARCHAR NOT NULL,
    size_bytes BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);