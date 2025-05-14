CREATE TABLE IF NOT EXISTS users_management.credit_cards
(
    id                BIGINT NOT NULL,
    payment_method_id BIGINT NOT NULL,
    card_number       TEXT   NOT NULL,
    cardholder_name   TEXT   NOT NULL,
    expiry_date       DATE   NOT NULL,
    card_type         TEXT   NOT NULL
);

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE table_schema = 'users_management'
          AND table_name = 'credit_cards'
          AND constraint_type = 'PRIMARY KEY'
    ) THEN
ALTER TABLE users_management.credit_cards ADD PRIMARY KEY (id);
END IF;
END;
$$;
