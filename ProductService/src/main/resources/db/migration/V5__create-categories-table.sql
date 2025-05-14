CREATE TABLE IF NOT EXISTS catalog.categories (
    id UUID,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    systemDefault BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'catalog'
          AND table_name = 'categories'
          AND constraint_type = 'PRIMARY KEY'
    ) THEN
ALTER TABLE catalog.categories ADD PRIMARY KEY (id);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'catalog'
          AND table_name = 'categories'
          AND constraint_name = 'categories_name_unique'
    ) THEN
ALTER TABLE catalog.categories ADD CONSTRAINT categories_name_unique UNIQUE(name);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_trigger
        WHERE tgname = 'update_categories_timestamp'
    ) THEN
CREATE TRIGGER update_categories_timestamp
    BEFORE UPDATE ON catalog.categories
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();
END IF;
END;
$$;