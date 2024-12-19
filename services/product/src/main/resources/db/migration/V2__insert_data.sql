-- Inserts para categorías
INSERT INTO category (id, name, description)
VALUES (nextval('category_id_seq'), 'Electrónicos', 'Productos electrónicos y gadgets');

INSERT INTO category (id, name, description)
VALUES (nextval('category_id_seq'), 'Hogar', 'Artículos para el hogar');

-- Inserts para productos
INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_id_seq'), 'Smartphone X', 'Teléfono inteligente última generación', 50.0, 599.99, 1);

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_id_seq'), 'Tablet Pro', 'Tablet de alto rendimiento', 30.0, 399.99, 1);

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_id_seq'), 'Cafetera Automática', 'Cafetera programable de 12 tazas', 25.0, 79.99, 2);

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_id_seq'), 'Juego de Sartenes', 'Set de 3 sartenes antiadherentes', 40.0, 89.99, 2);