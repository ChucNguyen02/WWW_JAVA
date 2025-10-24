


USE shoppingdb;


ALTER TABLE comment
    MODIFY COLUMN text TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL;

ALTER DATABASE shoppingdb
CHARACTER SET utf8mb4
COLLATE UTF8MB4_UNICODE_CI;



INSERT INTO role (id, name) VALUES
                                (1, 'ADMIN'),
                                (2, 'USER')
    ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO category (id, name) VALUES
                                    (1, 'Electronics'),
                                    (2, 'Books'),
                                    (3, 'Clothing')
    ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO customer (id, name, email, phone, address, customer_since) VALUES
                                                                           (1, 'Nguyen Van A', 'vana@example.com', '0909123456', '123 Le Loi, Q1, HCMC', '2023-01-15'),
                                                                           (2, 'Tran Thi B', 'thib@example.com', '0909876543', '45 Nguyen Trai, Q5, HCMC', '2022-11-30'),
                                                                           (3, 'Nguoi Dung User', 'user@example.com', '0909111222', '789 Cach Mang, Q10, HCMC', '2025-01-01')
    ON DUPLICATE KEY UPDATE
                         name = VALUES(name),
                         email = VALUES(email),
                         phone = VALUES(phone),
                         address = VALUES(address),
                         customer_since = VALUES(customer_since);

INSERT INTO app_user (id, username, password, email, enabled, customer_id) VALUES
                                                                               (1, 'admin', '$2a$10$2hcES.rL2cYRqGaD/u3IAOcT0bh6nO21Yi4V.8OkOGKGkSD3pxnDa', 'admin@example.com', 1, NULL),
                                                                               (2, 'user', '$2a$10$2hcES.rL2cYRqGaD/u3IAOcT0bh6nO21Yi4V.8OkOGKGkSD3pxnDa', 'user@example.com', 1, 3)
    ON DUPLICATE KEY UPDATE
                         username = VALUES(username),
                         password = VALUES(password),
                         email = VALUES(email),
                         enabled = VALUES(enabled),
                         customer_id = VALUES(customer_id);

INSERT INTO app_user_role (user_id, role_id) VALUES
                                                 (1, 1),
                                                 (2, 2)
    ON DUPLICATE KEY UPDATE
                         user_id = VALUES(user_id),
                         role_id = VALUES(role_id);

INSERT INTO product (id, name, price, stock_quantity, category_id) VALUES
                                                                       (1, 'Laptop Pro M3', 25000000.00, 10, 1),
                                                                       (2, 'Mouse Genius', 350000.50, 15, 1),
                                                                       (3, 'Java Spring Boot', 550000.00, 20, 2),
                                                                       (4, 'Ao Thun Polo', 400000.00, 8, 3),
                                                                       (5, 'Sach Luyen Code', 150000.00, 0, 2)
    ON DUPLICATE KEY UPDATE
                         name = VALUES(name),
                         price = VALUES(price),
                         stock_quantity = VALUES(stock_quantity),
                         category_id = VALUES(category_id);

INSERT INTO `order`
(id, order_date, status, total_amount, shipping_address, payment_method, customer_id) VALUES
                                                                                          (1, '2025-10-01 10:30:00', 'PENDING', 25350000.50, '123 Le Loi, Q1, HCMC', 'COD', 1),
                                                                                          (2, '2025-10-05 14:15:00', 'PROCESSING', 1100000.00, '45 Nguyen Trai, Q5, HCMC', 'BANK', 2),
                                                                                          (3, '2025-10-10 09:00:00', 'DELIVERED', 1750000.00, '123 Le Loi, Q1, HCMC', 'COD', 1)
    ON DUPLICATE KEY UPDATE
                         order_date = VALUES(order_date),
                         status = VALUES(status),
                         total_amount = VALUES(total_amount),
                         shipping_address = VALUES(shipping_address),
                         payment_method = VALUES(payment_method),
                         customer_id = VALUES(customer_id);

INSERT INTO order_line (order_id, product_id, amount, purchase_price) VALUES
                                                                          (1, 1, 1, 25000000.00),
                                                                          (1, 2, 1, 350000.50),
                                                                          (2, 3, 2, 550000.00),
                                                                          (3, 4, 3, 400000.00),
                                                                          (3, 3, 1, 550000.00)
    ON DUPLICATE KEY UPDATE
                         amount = VALUES(amount),
                         purchase_price = VALUES(purchase_price);

INSERT INTO comment (id, text, product_id, author_id, created_at) VALUES
                                                                      (1, 'Máy chạy rất mượt!', 1, 2, '2025-10-15 14:00:00'),
                                                                      (2, 'Sách hay và chi tiết.', 3, 2, '2025-10-16 09:30:00'),
                                                                      (3, 'Pin tốt, rất đáng tiền.', 1, 2, '2025-10-17 11:15:00'),
                                                                      (4, 'Chuột nhạy, cầm nắm thoải mái.', 2, 2, '2025-10-18 13:45:00')
    ON DUPLICATE KEY UPDATE
                         text = VALUES(text),
                         created_at = VALUES(created_at),
                         product_id = VALUES(product_id),
                         author_id = VALUES(author_id);

INSERT INTO cart (id, user_id) VALUES
    (1, 2)
    ON DUPLICATE KEY UPDATE
                         user_id = VALUES(user_id);

INSERT INTO cart_item (id, cart_id, product_id, quantity) VALUES
    (1, 1, 2, 1)
    ON DUPLICATE KEY UPDATE
                         quantity = VALUES(quantity);