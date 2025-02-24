CREATE TABLE catalog.sales_metrics
(
    product_id UUID,
    total_sold BIGINT    NOT NULL,
    last_sold  TIMESTAMP NOT NULL
);
