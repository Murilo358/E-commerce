CREATE TABLE IF NOT EXISTS users_management.users(
    id BIGSERIAL  NOT NULL,
    name TEXT NOT NULL,
    cpf TEXT NOT NULL,
    birth_date DATE NOT NULL,
    email TEXT NULL,
    password TEXT NOT NULL,
    role INTEGER NOT NULL,
    profile_id BIGINT NOT NULL
);
ALTER TABLE
    users_management.users ADD PRIMARY KEY(id);

ALTER TABLE
    users_management.users ADD CONSTRAINT users_id_foreign FOREIGN KEY(profile_id) REFERENCES users_management.profile(id);