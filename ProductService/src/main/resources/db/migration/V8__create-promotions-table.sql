CREATE TABLE catalog.promotions
(
    id          BIGSERIAL NOT NULL,
    product_id  UUID      NOT NULL,
    new_price   BIGINT    NOT NULL,
    description TEXT      NOT NULL,
    start_date  DATE      NOT NULL,
    end_date    DATE      NOT NULL
);
ALTER TABLE
    catalog.promotions
    ADD PRIMARY KEY (id);

ALTER TABLE
    catalog.promotions
    ADD CONSTRAINT promotions_product_id_foreign FOREIGN KEY (product_id) REFERENCES catalog.products (id);