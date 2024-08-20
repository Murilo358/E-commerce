CREATE TABLE catalog.produts_reviews(
    id BIGSERIAL  NOT NULL,
    product_id UUID NOT NULL,
    user_id BIGINT NOT NULL,
    rating SMALLINT NOT NULL,
    comment TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE
    catalog.produts_reviews ADD PRIMARY KEY(id);

ALTER TABLE
    catalog.produts_reviews ADD CONSTRAINT produts_reviews_product_id_foreign FOREIGN KEY(product_id) REFERENCES catalog.products(id);


ALTER TABLE
    catalog.produts_reviews ADD CONSTRAINT produts_reviews_user_id_foreign FOREIGN KEY(user_id) REFERENCES users_control.users(id);