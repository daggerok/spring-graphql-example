DROP TABLE orders IF EXISTS
;

CREATE TABLE orders(
    id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL
)
;
