CREATE TABLE IF NOT EXISTS catalog.products(
                                               id UUID,
                                               name TEXT NOT NULL,
                                               description TEXT NOT NULL,
                                               price DOUBLE PRECISION NOT NULL,
                                               seller_id BIGINT NOT NULL,
                                               category_id UUID NOT NULL,
                                               inventory_count BIGINT NOT NULL,
                                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                               updated_at TIMESTAMP
);
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'catalog'
          AND table_name = 'products'
          AND constraint_type = 'PRIMARY KEY'
    ) THEN
ALTER TABLE catalog.products ADD PRIMARY KEY (id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_trigger
        WHERE tgname = 'update_product_timestamp'
    ) THEN
CREATE TRIGGER update_product_timestamp
    BEFORE UPDATE ON catalog.products
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'catalog'
          AND table_name = 'products'
          AND constraint_name = 'products_category_id_foreign'
    ) THEN
ALTER TABLE catalog.products
    ADD CONSTRAINT products_category_id_foreign
        FOREIGN KEY (category_id)
            REFERENCES catalog.categories(id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'catalog'
          AND table_name = 'products'
          AND constraint_name = 'products_seller_id_foreign'
    ) THEN
ALTER TABLE catalog.products
    ADD CONSTRAINT products_seller_id_foreign
        FOREIGN KEY (seller_id)
            REFERENCES users_management.users(id);
END IF;
END;
$$;
