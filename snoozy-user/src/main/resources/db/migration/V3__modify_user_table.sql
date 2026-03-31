ALTER TABLE users
ADD CONSTRAINT fk_avatar
FOREIGN KEY (avatar_id)
REFERENCES avatars(id);