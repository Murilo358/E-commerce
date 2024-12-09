CREATE TABLE users_management.profile(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    payment_methods jsonb NOT NULL,
    country_code BIGINT NOT NULL,
    postal_code TEXT NOT NULL,
    contact_phone BIGINT NOT NULL
);

ALTER TABLE users_management.profile ADD PRIMARY KEY (id);
