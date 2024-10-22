CREATE TABLE users_management.payment_methods
(
    id              BIGINT NOT NULL,
    payment_type_id BIGINT NOT NULL,
    user_id         BIGINT NOT NULL,
    is_default      BIGINT NOT NULL
);
ALTER TABLE
    payment_methods
    ADD PRIMARY KEY (id);

ALTER TABLE
    users_management.payment_methods ADD CONSTRAINT payment_methods_payment_type_foreign FOREIGN KEY(payment_type) REFERENCES users_management.payment_types(id);