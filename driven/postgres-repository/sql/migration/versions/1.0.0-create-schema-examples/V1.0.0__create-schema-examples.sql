DROP TABLE IF EXISTS public.O_EXAMPLES CASCADE;
DROP SEQUENCE IF EXISTS public.o_esamples_id_seq;

CREATE TABLE product (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(90) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE store (
    store_id SERIAL PRIMARY KEY,
    name VARCHAR(90) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE batch_product_store (
    batch_id SERIAL PRIMARY KEY,
    product_id INT NOT NULL,
    store_id INT NOT NULL,
    entry_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    is_discounted BOOLEAN NOT NULL DEFAULT FALSE,
    discount_date DATE,
    removed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(product_id),
    CONSTRAINT fk_store FOREIGN KEY (store_id) REFERENCES store(store_id),
    CONSTRAINT unique_batch UNIQUE (product_id, store_id, entry_date)
);
