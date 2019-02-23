CREATE TABLE IF NOT EXISTS groups (
    id uuid primary key,
    name varchar(32)
);

CREATE UNIQUE INDEX group_name_idx on groups (name);
