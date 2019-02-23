CREATE TABLE IF NOT EXISTS applications (
    id uuid primary key,
    name varchar(32)
);

CREATE UNIQUE INDEX application_name_idx on applications (name);
