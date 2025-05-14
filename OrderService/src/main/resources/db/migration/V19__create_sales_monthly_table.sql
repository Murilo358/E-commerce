CREATE TABLE IF NOT EXISTS catalog.sales_monthly
(
    product_id UUID,
    month      DATE   NOT NULL,
    total_sold BIGINT NOT NULL DEFAULT 0,
    PRIMARY KEY (product_id, month)
);
