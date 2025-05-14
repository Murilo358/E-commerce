CREATE TABLE IF NOT EXISTS users_management.profile(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    payment_methods jsonb NOT NULL,
    country_code BIGINT NOT NULL,
    postal_code TEXT NOT NULL,
    contact_phone BIGINT NOT NULL
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'users_management'
          AND table_name = 'profile'
          AND constraint_type = 'PRIMARY KEY'
    ) THEN
ALTER TABLE users_management.profile ADD PRIMARY KEY (id);
END IF;
END;
$$;
