CREATE TABLE gateway.users(
    id BIGINT NOT NULL,
    name TEXT NOT NULL,
    cpf TEXT NOT NULL,
    birth_date DATE NOT NULL,
    email TEXT NULL,
    password TEXT NOT NULL,
    role INTEGER NOT NULL
);
ALTER TABLE
    gateway.users ADD PRIMARY KEY(id);