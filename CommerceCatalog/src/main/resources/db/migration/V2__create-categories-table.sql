CREATE TABLE catalog.categories (
    id BIGINT NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL
);

ALTER TABLE
    catalog.categories ADD PRIMARY KEY(id);

ALTER TABLE
catalog.categories ADD CONSTRAINT categories_name_unique UNIQUE(name);