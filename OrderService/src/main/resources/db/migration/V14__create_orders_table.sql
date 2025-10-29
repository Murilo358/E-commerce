CREATE TABLE IF NOT EXISTS transactions.orders (
    id              UUID PRIMARY KEY,
    buyer_id        BIGINT       ,
    products        JSONB        ,
    payment_method  BIGINT       ,
    total_price     DECIMAL(10,2) ,
    weight          DECIMAL(10,2) ,
    status          VARCHAR(50)  DEFAULT 'PENDING'
    CHECK (status IN ('PENDING', 'APPROVED', 'CANCELED', 'SHIPPED', 'DELIVERED')),
    shipping_info   JSONB,
    canceled_at     TIMESTAMP WITH TIME ZONE,
    shipped_at      TIMESTAMP WITH TIME ZONE,
    delivered_at    TIMESTAMP WITH TIME ZONE,
    created_at      TIMESTAMP WITH TIME ZONE  DEFAULT CURRENT_TIMESTAMP,
    approved_at     TIMESTAMP WITH TIME ZONE,
    updated_at      TIMESTAMP WITH TIME ZONE,
    CONSTRAINT orders_buyer_id_foreign
    FOREIGN KEY (buyer_id) REFERENCES users_management.users(id)
);

CREATE INDEX IF NOT EXISTS idx_orders_buyer_id    ON transactions.orders (buyer_id);
CREATE INDEX IF NOT EXISTS idx_orders_status      ON transactions.orders (status);
CREATE INDEX IF NOT EXISTS idx_orders_created_at  ON transactions.orders (created_at);
CREATE INDEX IF NOT EXISTS idx_orders_updated_at  ON transactions.orders (updated_at);
