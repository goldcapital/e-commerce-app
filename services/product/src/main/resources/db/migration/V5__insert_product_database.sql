-- Products for category_id = 1 (Keyboards)
INSERT INTO product (id, available_quantity, description, name, price, category_id) VALUES
                                                                                        (nextval('product_seq'), 10, 'Mechanical keyboard with RGB lighting', 'Mechanical Keyboard', 89.99, 1),
                                                                                        (nextval('product_seq'), 15, 'Wireless compact keyboard', 'Wireless Keyboard', 59.50, 1),
                                                                                        (nextval('product_seq'), 8, 'Ergonomic split keyboard', 'Ergonomic Keyboard', 109.99, 1),
                                                                                        (nextval('product_seq'), 12, 'Compact travel keyboard', 'Travel Keyboard', 45.00, 1),
                                                                                        (nextval('product_seq'), 5, 'Backlit keyboard with numeric pad', 'Backlit Keyboard', 69.99, 1);

-- Products for category_id = 2 (Monitors)
INSERT INTO product (id, available_quantity, description, name, price, category_id) VALUES
                                                                                        (nextval('product_seq'), 6, '27-inch 4K UHD Monitor', '4K Monitor', 299.99, 51),
                                                                                        (nextval('product_seq'), 10, '24-inch Full HD Monitor', 'HD Monitor', 159.00, 51),
                                                                                        (nextval('product_seq'), 7, 'Curved gaming monitor 144Hz', 'Gaming Monitor', 399.50, 51),
                                                                                        (nextval('product_seq'), 4, 'USB-C portable monitor', 'Portable Monitor', 199.99, 51),
                                                                                        (nextval('product_seq'), 3, 'Touch screen display monitor', 'Touch Monitor', 229.00, 51);

-- Products for category_id = 3 (Screens)
INSERT INTO product (id, available_quantity, description, name, price, category_id) VALUES
                                                                                        (nextval('product_seq'), 11, 'Portable projector screen 60"', 'Portable Screen', 79.99, 101),
                                                                                        (nextval('product_seq'), 9, 'Pull-down screen for wall mount', 'Wall Screen', 119.50, 101),
                                                                                        (nextval('product_seq'), 6, 'Tripod projector screen 100"', 'Tripod Screen', 149.99, 101),
                                                                                        (nextval('product_seq'), 4, 'Electric motorized screen 120"', 'Motorized Screen', 249.00, 101),
                                                                                        (nextval('product_seq'), 2, 'Ceiling-mounted screen', 'Ceiling Screen', 189.00, 101);

-- Products for category_id = 4 (Mice)
INSERT INTO product (id, available_quantity, description, name, price, category_id) VALUES
                                                                                        (nextval('product_seq'), 18, 'Ergonomic wireless mouse', 'Wireless Mouse', 39.99, 151),
                                                                                        (nextval('product_seq'), 14, 'Gaming mouse with programmable buttons', 'Gaming Mouse', 59.99, 151),
                                                                                        (nextval('product_seq'), 10, 'Bluetooth silent click mouse', 'Bluetooth Mouse', 29.99, 151),
                                                                                        (nextval('product_seq'), 8, 'Vertical mouse for wrist support', 'Vertical Mouse', 49.00, 151),
                                                                                        (nextval('product_seq'), 7, 'Trackpad with multi-touch', 'Trackpad', 69.99, 151);

-- Products for category_id = 5 (Accessories)
INSERT INTO product (id, available_quantity, description, name, price, category_id) VALUES
                                                                                        (nextval('product_seq'), 25, 'Adjustable laptop stand with cooling fan', 'Laptop Stand', 45.99, 201),
                                                                                        (nextval('product_seq'), 20, 'Wireless charging pad for smartphones', 'Charging Pad', 18.50, 201),
                                                                                        (nextval('product_seq'), 28, 'Gaming headset stand with RGB lighting', 'Headset Stand', 32.00, 201),
                                                                                        (nextval('product_seq'), 22, 'Bluetooth mechanical keypad for tablets', 'Bluetooth Keypad', 27.99, 201),
                                                                                        (nextval('product_seq'), 30, 'External hard drive enclosure with USB-C', 'HDD Enclosure', 24.75, 201);
