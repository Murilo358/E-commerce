CREATE TABLE users_management.users_reviews(
    id BIGSERIAL NOT NULL,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    rate BIGINT NOT NULL,
    comment TEXT NOT NULL
);
ALTER TABLE
    users_management.users_reviews ADD PRIMARY KEY(id);

ALTER TABLE
    users_management.users_reviews ADD CONSTRAINT users_reviews_user_id_foreign FOREIGN KEY(user_id) REFERENCES users_management.users(id);