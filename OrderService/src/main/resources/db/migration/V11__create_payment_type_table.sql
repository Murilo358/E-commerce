CREATE TABLE IF NOT EXISTS users_management.payment_types
(
    id   BIGINT NOT NULL,
    name TEXT   NOT NULL
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'users_management'
          AND table_name = 'payment_types'
          AND constraint_type = 'PRIMARY KEY'
    ) THEN
ALTER TABLE users_management.payment_types ADD PRIMARY KEY (id);
END IF;
END;
$$;
