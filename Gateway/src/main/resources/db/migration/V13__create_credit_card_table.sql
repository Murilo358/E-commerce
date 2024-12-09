CREATE TABLE users_management.credit_cards
(
    id                BIGINT NOT NULL,
    payment_method_id BIGINT NOT NULL,
    card_number       TEXT   NOT NULL,
    cardholder_name   TEXT   NOT NULL,
    expiry_date       DATE   NOT NULL,
    card_type         TEXT   NOT NULL
);
ALTER TABLE
    users_management.credit_cards
    ADD PRIMARY KEY (id);