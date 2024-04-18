CREATE TABLE catalog.products(
    id BIGINT NOT NULL,
    name TEXT NOT NULL,
    Description TEXT NOT NULL,
    Price DOUBLE PRECISION NOT NULL,
    category_id BIGINT NOT NULL,
    inventory_count BIGINT NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL
);
ALTER TABLE
    catalog.products ADD PRIMARY KEY(id);

ALTER TABLE
    catalog.products ADD CONSTRAINT products_category_id_foreign FOREIGN KEY(category_id) REFERENCES catalog.categories(id);