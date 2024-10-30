CREATE TABLE transactions.orders
(
    id             UUID,
    products       jsonb               NOT NULL,
    payment_method BIGINT              NOT NULL,
    total_price    DECIMAL(10, 2)      NOT NULL,
    created_at     DATE                NOT NULL,
    updated_at     DATE                DEFAULT CURRENT_DATE,
    buyer_id       BIGINT              NOT NULL,
    status         VARCHAR(50)         NOT NULL DEFAULT 'pending',
    weight         DECIMAL(10, 2)      NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE transactions.orders
    ADD CONSTRAINT orders_buyer_id_foreign FOREIGN KEY (buyer_id) REFERENCES users_management.users (id);

ALTER TABLE
    transactions.orders
    ADD CONSTRAINT orders_seller_id_foreign FOREIGN KEY (seller_id) REFERENCES users_management.users (id);


CREATE INDEX idx_seller_id ON transactions.orders (seller_id);
CREATE INDEX idx_buyer_id ON transactions.orders (buyer_id);
CREATE INDEX idx_created_at ON transactions.orders (created_at);

