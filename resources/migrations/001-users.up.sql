CREATE TABLE IF NOT EXISTS users (
    id uuid primary key,
    username varchar(32),
    password varchar(255)
);

CREATE UNIQUE INDEX username_idx on users (username);
