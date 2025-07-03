CREATE TABLE IF NOT EXISTS transactions.orders
(
    id             UUID,
    products       jsonb                    NOT NULL,
    payment_method BIGINT                   NOT NULL,
    total_price    DECIMAL(10, 2)           NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    buyer_id       BIGINT                   NOT NULL,
    status         VARCHAR(50)              NOT NULL DEFAULT 'pending' CHECK (status IN ('pending', 'approved', 'canceled', 'shipped', 'delivered')),
    weight         DECIMAL(10, 2)           NOT NULL,
    PRIMARY KEY (id)
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'transactions'
          AND table_name = 'orders'
          AND constraint_name = 'orders_buyer_id_foreign'
    ) THEN
ALTER TABLE transactions.orders
    ADD CONSTRAINT orders_buyer_id_foreign
        FOREIGN KEY (buyer_id)
            REFERENCES users_management.users(id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'transactions'
          AND table_name = 'orders'
          AND constraint_name = 'orders_seller_id_foreign'
    ) THEN
ALTER TABLE transactions.orders
    ADD CONSTRAINT orders_seller_id_foreign
        FOREIGN KEY (seller_id)
            REFERENCES users_management.users(id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_indexes
        WHERE schemaname = 'transactions'
          AND tablename = 'orders'
          AND indexname = 'idx_seller_id'
    ) THEN
CREATE INDEX idx_seller_id ON transactions.orders (seller_id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_indexes
        WHERE schemaname = 'transactions'
          AND tablename = 'orders'
          AND indexname = 'idx_buyer_id'
    ) THEN
CREATE INDEX idx_buyer_id ON transactions.orders (buyer_id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_indexes
        WHERE schemaname = 'transactions'
          AND tablename = 'orders'
          AND indexname = 'idx_created_at'
    ) THEN
CREATE INDEX idx_created_at ON transactions.orders (created_at);
END IF;
END;
$$;
