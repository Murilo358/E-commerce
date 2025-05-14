CREATE TABLE IF NOT EXISTS catalog.produts_reviews(
    id BIGSERIAL  NOT NULL,
    product_id UUID NOT NULL,
    user_id BIGINT NOT NULL,
    rating SMALLINT NOT NULL,
    comment TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'catalog'
          AND table_name = 'produts_reviews'
          AND constraint_type = 'PRIMARY KEY'
    ) THEN
ALTER TABLE catalog.produts_reviews ADD PRIMARY KEY (id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'catalog'
          AND table_name = 'produts_reviews'
          AND constraint_name = 'produts_reviews_product_id_foreign'
    ) THEN
ALTER TABLE catalog.produts_reviews
    ADD CONSTRAINT produts_reviews_product_id_foreign
        FOREIGN KEY (product_id)
            REFERENCES catalog.products(id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'catalog'
          AND table_name = 'produts_reviews'
          AND constraint_name = 'produts_reviews_user_id_foreign'
    ) THEN
ALTER TABLE catalog.produts_reviews
    ADD CONSTRAINT produts_reviews_user_id_foreign
        FOREIGN KEY (user_id)
            REFERENCES users_management.users(id);
END IF;
END;
$$;
