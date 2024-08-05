ALTER TABLE users DROP COLUMN username;

ALTER TABLE users ADD COLUMN id SERIAL PRIMARY KEY;

ALTER TABLE users ADD COLUMN authority_id int REFERENCES authorities(id);

ALTER TABLE users ADD COLUMN username varchar not null default 'user' unique;