CREATE TABLE catalog.categories (
    id UUID,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    systemDefault BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

ALTER TABLE
    catalog.categories ADD PRIMARY KEY(id);

ALTER TABLE
catalog.categories ADD CONSTRAINT categories_name_unique UNIQUE(name);

CREATE TRIGGER update_categories_timestamp
    BEFORE UPDATE ON catalog.categories
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();