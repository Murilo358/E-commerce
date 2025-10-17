CREATE TABLE IF NOT EXISTS transactions.orders (
    id              UUID PRIMARY KEY,
    buyer_id        BIGINT       NOT NULL,
    products        JSONB        NOT NULL,
    payment_method  BIGINT       NOT NULL,
    total_price     DECIMAL(10,2) NOT NULL,
    weight          DECIMAL(10,2) NOT NULL,
    status          VARCHAR(50)  NOT NULL DEFAULT 'pending'
    CHECK (status IN ('pending', 'approved', 'canceled', 'shipped', 'delivered')),
    shipping_info   BIGINT,
    canceled_at     TIMESTAMP WITH TIME ZONE,
    shipped_at      TIMESTAMP WITH TIME ZONE,
    delivered_at    TIMESTAMP WITH TIME ZONE,
    created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    approved_at     TIMESTAMP WITH TIME ZONE,
    updated_at      TIMESTAMP WITH TIME ZONE,
    CONSTRAINT orders_buyer_id_foreign
    FOREIGN KEY (buyer_id) REFERENCES users_management.users(id)
);

CREATE INDEX IF NOT EXISTS idx_orders_buyer_id    ON transactions.orders (buyer_id);
CREATE INDEX IF NOT EXISTS idx_orders_status      ON transactions.orders (status);
CREATE INDEX IF NOT EXISTS idx_orders_created_at  ON transactions.orders (created_at);
CREATE INDEX IF NOT EXISTS idx_orders_updated_at  ON transactions.orders (updated_at);
