CREATE TABLE catalog.products(
    id UUID DEFAULT gen_random_uuid() NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    seller_id BIGINT NOT NULL,
    category_id UUID NOT NULL,
    inventory_count BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

ALTER TABLE
    catalog.products ADD PRIMARY KEY(id);

CREATE TRIGGER update_product_timestamp
BEFORE UPDATE ON catalog.products
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

ALTER TABLE
    catalog.products ADD CONSTRAINT products_category_id_foreign FOREIGN KEY(category_id) REFERENCES catalog.categories(id);


ALTER TABLE
    catalog.products ADD CONSTRAINT products_seller_id_foreign FOREIGN KEY(seller_id) REFERENCES gateway.users(id);