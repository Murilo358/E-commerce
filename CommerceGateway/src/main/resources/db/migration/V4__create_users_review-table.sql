CREATE TABLE gateway.users_reviews(
    id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    rate BIGINT NOT NULL,
    comment TEXT NOT NULL
);
ALTER TABLE
    gateway.users_reviews ADD PRIMARY KEY(id);

ALTER TABLE
    gateway.users_reviews ADD CONSTRAINT users_reviews_user_id_foreign FOREIGN KEY(user_id) REFERENCES gateway.users(id);