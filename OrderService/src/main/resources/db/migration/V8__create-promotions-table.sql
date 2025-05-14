CREATE TABLE IF NOT EXISTS catalog.promotions
(
    id          BIGSERIAL NOT NULL,
    product_id  UUID      NOT NULL,
    new_price   BIGINT    NOT NULL,
    description TEXT      NOT NULL,
    start_date  TIMESTAMP WITH TIME ZONE       NOT NULL ,
    end_date    TIMESTAMP WITH TIME ZONE       NOT NULL
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'catalog'
          AND table_name = 'promotions'
          AND constraint_type = 'PRIMARY KEY'
    ) THEN
ALTER TABLE catalog.promotions ADD PRIMARY KEY (id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'catalog'
          AND table_name = 'promotions'
          AND constraint_name = 'promotions_product_id_foreign'
    ) THEN
ALTER TABLE catalog.promotions
    ADD CONSTRAINT promotions_product_id_foreign
        FOREIGN KEY (product_id)
            REFERENCES catalog.products(id);
END IF;
END;
$$;
