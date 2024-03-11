# CREATE TABLE categories
# (
#     id   SERIAL PRIMARY KEY,
#     name VARCHAR(100) NOT NULL
# );
#
# CREATE TABLE products
# (
#     id          SERIAL PRIMARY KEY,
#     name        VARCHAR(100) NOT NULL,
#     price       NUMERIC(10, 2) NOT NULL,
#     created_at  DATE NOT NULL,
#     category_id INT NOT NULL,
#     photo       VARCHAR(255),
#
#     FOREIGN KEY (category_id) REFERENCES categories (id)
# );