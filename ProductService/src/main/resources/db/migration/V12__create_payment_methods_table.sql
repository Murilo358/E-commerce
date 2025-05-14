CREATE TABLE IF NOT EXISTS users_management.payment_methods
(
    id              BIGINT NOT NULL,
    payment_type_id BIGINT NOT NULL,
    user_id         BIGINT NOT NULL,
    is_default     BOOLEAN NOT NULL
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'users_management'
          AND table_name = 'payment_methods'
          AND constraint_type = 'PRIMARY KEY'
    ) THEN
ALTER TABLE users_management.payment_methods ADD PRIMARY KEY (id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'users_management'
          AND table_name = 'payment_methods'
          AND constraint_name = 'payment_methods_payment_type_foreign'
    ) THEN
ALTER TABLE users_management.payment_methods
    ADD CONSTRAINT payment_methods_payment_type_foreign
        FOREIGN KEY (payment_type_id)
            REFERENCES users_management.payment_types(id);
END IF;
END;
$$;
