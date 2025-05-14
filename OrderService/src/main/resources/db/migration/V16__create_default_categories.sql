DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM catalog.categories WHERE id = 'f05ff978-2746-4b73-8018-098bdf9fcee7'
    ) THEN
        INSERT INTO catalog.categories (id, name, description, systemdefault)
        VALUES ('f05ff978-2746-4b73-8018-098bdf9fcee7', 'categories.electronics.name', 'categories.electronics.description', true);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM catalog.categories WHERE id = 'e1799214-84a8-4467-ae1e-eef2b81a211e'
    ) THEN
        INSERT INTO catalog.categories (id, name, description, systemdefault)
        VALUES ('e1799214-84a8-4467-ae1e-eef2b81a211e', 'categories.games.name', 'categories.games.description', true);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM catalog.categories WHERE id = 'f58b9e43-0320-4bd9-9f9a-a9bdb9a54b19'
    ) THEN
        INSERT INTO catalog.categories (id, name, description, systemdefault)
        VALUES ('f58b9e43-0320-4bd9-9f9a-a9bdb9a54b19', 'categories.clothes.name', 'categories.clothes.description', true);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM catalog.categories WHERE id = 'a166d0ac-009e-4dd6-bb1a-d1e173b2c3ba'
    ) THEN
        INSERT INTO catalog.categories (id, name, description, systemdefault)
        VALUES ('a166d0ac-009e-4dd6-bb1a-d1e173b2c3ba', 'categories.books.name', 'categories.books.description', true);
END IF;
END;
$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM catalog.categories WHERE id = '124b78da-7526-4e39-94f9-8b4e166a04d1'
    ) THEN
        INSERT INTO catalog.categories (id, name, description, systemdefault)
        VALUES ('124b78da-7526-4e39-94f9-8b4e166a04d1', 'categories.furniture.name', 'categories.furniture.description', true);
END IF;
END;
$$;
