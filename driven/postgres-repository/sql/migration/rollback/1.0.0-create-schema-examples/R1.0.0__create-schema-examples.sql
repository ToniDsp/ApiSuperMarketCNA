DROP TABLE IF EXISTS batch_product_store CASCADE;
DROP TABLE IF EXISTS store CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DELETE FROM flyway_schema_history WHERE version = '1.0.0';
