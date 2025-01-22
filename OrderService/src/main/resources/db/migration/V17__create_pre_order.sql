CREATE TABLE transactions.pre_order
(
    id           UUID PRIMARY KEY,
    user_id      BIGINT         NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at   TIMESTAMP,
    total        DECIMAL(10, 2) NOT NULL,
    products_ids INTEGER[]    NOT NULL
);
