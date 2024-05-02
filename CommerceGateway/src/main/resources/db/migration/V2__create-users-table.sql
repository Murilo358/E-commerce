CREATE TABLE gateway.users(
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
    gateway.users ADD PRIMARY KEY(id);