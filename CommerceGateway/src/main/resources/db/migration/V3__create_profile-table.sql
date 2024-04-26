CREATE TABLE gateway.profile(
    id BIGINT NOT NULL,
    payment_methods jsonb NOT NULL,
    country_code BIGINT NOT NULL,
    postal_code TEXT NOT NULL,
    contact_phone BIGINT NOT NULL
);

ALTER TABLE
    gateway.profile ADD PRIMARY KEY(id);

ALTER TABLE
    gateway.users ADD CONSTRAINT users_id_foreign FOREIGN KEY(id) REFERENCES gateway.profile(id);