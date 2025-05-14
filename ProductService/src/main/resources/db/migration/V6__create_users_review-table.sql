CREATE TABLE IF NOT EXISTS users_management.users_reviews(
    id BIGSERIAL NOT NULL,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    rate BIGINT NOT NULL,
    comment TEXT NOT NULL
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'users_management'
          AND table_name = 'users_reviews'
          AND constraint_type = 'PRIMARY KEY'
    ) THEN
ALTER TABLE users_management.users_reviews ADD PRIMARY KEY (id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'users_management'
          AND table_name = 'users_reviews'
          AND constraint_name = 'users_reviews_user_id_foreign'
    ) THEN
ALTER TABLE users_management.users_reviews
    ADD CONSTRAINT users_reviews_user_id_foreign
        FOREIGN KEY (user_id)
            REFERENCES users_management.users(id);
END IF;
END;
$$;
