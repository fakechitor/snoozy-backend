ALTER TABLE group_members
    ADD COLUMN id BIGSERIAL;

ALTER TABLE group_members
    ADD CONSTRAINT group_members_pkey PRIMARY KEY (id);

ALTER TABLE group_members
    ADD CONSTRAINT uk_group_members_group_user UNIQUE (group_id, user_id);