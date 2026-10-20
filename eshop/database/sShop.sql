DROP DATABASE IF EXISTS eshop;
CREATE DATABASE eshop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE eshop;

-- 1. Bảng category (15 dòng mẫu)
CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL UNIQUE,
                          description VARCHAR(255)
);

-- 2. Bảng product (50 dòng mẫu)
CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(150) NOT NULL,
                         description VARCHAR(500),
                         price DECIMAL(15,2) NOT NULL,
                         quantity INT NOT NULL DEFAULT 0,
                         image_url VARCHAR(500),
                         category_id BIGINT NOT NULL,
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id)
);

-- 3. Bảng users (5 dòng mẫu - mật khẩu đều là 123456)
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       full_name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       phone VARCHAR(20),
                       address VARCHAR(255),
                       role VARCHAR(30) NOT NULL,
                       enabled BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 4. Bảng orders
CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                        customer_name VARCHAR(100) NOT NULL,
                        phone VARCHAR(20) NOT NULL,
                        address VARCHAR(255) NOT NULL,
                        note VARCHAR(500),
                        payment_method VARCHAR(20) NOT NULL,
                        status VARCHAR(20) NOT NULL DEFAULT 'NEW',
                        total_amount DECIMAL(15,2) NOT NULL,
                        user_id BIGINT NULL,
                        CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- 5. Bảng order_detail
CREATE TABLE order_detail (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              order_id BIGINT NOT NULL,
                              product_id BIGINT NOT NULL,
                              quantity INT NOT NULL,
                              unit_price DECIMAL(15,2) NOT NULL,
                              CONSTRAINT fk_detail_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                              CONSTRAINT fk_detail_product FOREIGN KEY (product_id) REFERENCES product(id)
);

-- ================= DỮ LIỆU MẪU =================

-- 15 Danh mục (category)
INSERT INTO category (name, description) VALUES
                                             ('Điện thoại', 'Smartphone các hãng'),
                                             ('Laptop', 'Máy tính xách tay văn phòng, gaming'),
                                             ('Máy tính bảng', 'Tablet Android và iPad'),
                                             ('Đồng hồ thông minh', 'Smartwatch theo dõi sức khoẻ'),
                                             ('Tai nghe', 'Tai nghe có dây và Bluetooth'),
                                             ('Loa Bluetooth', 'Loa di động và loa để bàn'),
                                             ('Bàn phím', 'Bàn phím cơ và văn phòng'),
                                             ('Chuột máy tính', 'Chuột không dây và gaming'),
                                             ('Màn hình', 'Màn hình máy tính độ phân giải cao'),
                                             ('Ổ cứng', 'SSD và HDD gắn trong, gắn ngoài'),
                                             ('Sạc dự phòng', 'Pin sạc dự phòng dung lượng cao'),
                                             ('Cáp & Củ sạc', 'Phụ kiện sạc nhanh các loại'),
                                             ('Ốp lưng & Bao da', 'Phụ kiện bảo vệ điện thoại, tablet'),
                                             ('Thiết bị mạng', 'Router Wi-Fi, bộ kích sóng'),
                                             ('Phụ kiện khác', 'Giá đỡ, balo, túi chống sốc, webcam');

-- 50 Sản phẩm (product) - khớp với kịch bản kiểm thử trong tài liệu
INSERT INTO product (name, description, price, quantity, image_url, category_id) VALUES
-- 1. Điện thoại (5 SP)
('iPhone 15 Pro Max 256GB', 'Chip A17 Pro, khung Titan tự nhiên', 29990000, 25, 'https://placehold.co/400x300?text=iPhone+15+Pro+Max', 1),
('iPhone 15 128GB', 'Màn hình Dynamic Island, camera 48MP', 19990000, 30, 'https://placehold.co/400x300?text=iPhone+15', 1),
('Samsung Galaxy S24 Ultra 256GB', 'Tích hợp Galaxy AI, bút S-Pen', 27990000, 20, 'https://placehold.co/400x300?text=Galaxy+S24+Ultra', 1),
('Xiaomi 14 256GB', 'Ống kính Leica, Snapdragon 8 Gen 3', 18990000, 15, 'https://placehold.co/400x300?text=Xiaomi+14', 1),
('OPPO Reno11 F 5G', 'Chuyên gia chân dung, sạc nhanh 67W', 8490000, 40, 'https://placehold.co/400x300?text=OPPO+Reno11+F', 1),
-- 2. Laptop (4 SP - đúng yêu cầu lọc ra 4 SP)
('MacBook Air M3 13 inch 8GB/256GB', 'Mỏng nhẹ, thời lượng pin 18 giờ', 26990000, 15, 'https://placehold.co/400x300?text=MacBook+Air+M3', 2),
('ASUS TUF Gaming F15', 'Core i5-12500H, RTX 3050, màn 144Hz', 18490000, 12, 'https://placehold.co/400x300?text=ASUS+TUF+F15', 2),
('Dell Inspiron 15 3530', 'Core i5-1335U, RAM 16GB, SSD 512GB', 16290000, 18, 'https://placehold.co/400x300?text=Dell+Inspiron+15', 2),
('Lenovo IdeaPad Slim 5 14IAH8', 'Core i5-12450H, màn OLED chuẩn màu', 15490000, 20, 'https://placehold.co/400x300?text=IdeaPad+Slim+5', 2),
-- 3. Máy tính bảng (4 SP)
('iPad Gen 10 64GB WiFi', 'Màn hình 10.9 inch Liquid Retina', 9490000, 25, 'https://placehold.co/400x300?text=iPad+Gen+10', 3),
('iPad Air 6 M2 11 inch 128GB', 'Hiệu năng mạnh mẽ với chip Apple M2', 16490000, 15, 'https://placehold.co/400x300?text=iPad+Air+M2', 3),
('Samsung Galaxy Tab S9 FE 128GB', 'Kèm bút S-Pen, kháng nước IP68', 9990000, 22, 'https://placehold.co/400x300?text=Galaxy+Tab+S9+FE', 3),
('Xiaomi Pad 6 8GB/256GB', 'Màn hình 144Hz WQHD+, Snapdragon 870', 7990000, 18, 'https://placehold.co/400x300?text=Xiaomi+Pad+6', 3),
-- 4. Đồng hồ thông minh (4 SP)
('Apple Watch Series 9 41mm GPS', 'Vi xử lý S9 SiP, chạm hai lần thông minh', 9690000, 20, 'https://placehold.co/400x300?text=Apple+Watch+S9', 4),
('Samsung Galaxy Watch6 40mm', 'Theo dõi giấc ngủ và BIA chuyên sâu', 5490000, 25, 'https://placehold.co/400x300?text=Galaxy+Watch6', 4),
('Garmin Forerunner 165', 'Đồng hồ chạy bộ GPS màn hình AMOLED', 6490000, 10, 'https://placehold.co/400x300?text=Forerunner+165', 4),
('Xiaomi Redmi Watch 4', 'Màn hình AMOLED 1.97 inch, pin 20 ngày', 2190000, 35, 'https://placehold.co/400x300?text=Redmi+Watch+4', 4),
-- 5. Tai nghe (4 SP)
('AirPods Pro 2 USB-C', 'Chống ồn chủ động ANC gấp 2 lần', 5790000, 30, 'https://placehold.co/400x300?text=AirPods+Pro+2', 5),
('Sony WH-1000XM5', 'Tai nghe chụp tai chống ồn đỉnh cao', 6990000, 12, 'https://placehold.co/400x300?text=Sony+WH-1000XM5', 5),
('JBL Tune 520BT', 'Tai nghe chụp tai không dây, pin 57 giờ', 1090000, 40, 'https://placehold.co/400x300?text=JBL+Tune+520BT', 5),
('Baseus Bowie E16', 'Tai nghe TWS Bluetooth 5.3 nhỏ gọn', 350000, 60, 'https://placehold.co/400x300?text=Baseus+E16', 5),
-- 6. Loa Bluetooth (3 SP)
('JBL Flip 6', 'Loa chống nước IP67, âm bass mạnh mẽ', 2890000, 20, 'https://placehold.co/400x300?text=JBL+Flip+6', 6),
('Marshall Emberton II', 'Thiết kế cổ điển, âm thanh 360 độ', 4290000, 14, 'https://placehold.co/400x300?text=Marshall+Emberton+II', 6),
('Sony SRS-XB100', 'Loa di động siêu nhỏ gọn, dây đeo tiện lợi', 1190000, 35, 'https://placehold.co/400x300?text=Sony+XB100', 6),
-- 7. Bàn phím (4 SP)
('Keychron K2 Pro Bluetooth', 'Bàn phím cơ QMK/VIA khung nhôm', 2390000, 15, 'https://placehold.co/400x300?text=Keychron+K2+Pro', 7),
('Logitech MX Keys S', 'Bàn phím văn phòng cao cấp gõ êm', 2690000, 20, 'https://placehold.co/400x300?text=MX+Keys+S', 7),
('Logitech K380s Pebble Keys 2', 'Bàn phím Bluetooth đa thiết bị nhỏ gọn', 790000, 45, 'https://placehold.co/400x300?text=Logitech+K380s', 7),
('AKKO 3087 RF Dragon Ball', 'Bàn phím cơ TKL kết nối không dây 2.4Ghz', 1590000, 18, 'https://placehold.co/400x300?text=AKKO+3087', 7),
-- 8. Chuột máy tính (4 SP)
('Logitech MX Master 3S', 'Chuột công thái học cuộn siêu tốc MagSpeed', 2390000, 25, 'https://placehold.co/400x300?text=MX+Master+3S', 8),
('Logitech G102 Lightsync', 'Chuột gaming quốc dân LED RGB', 420000, 80, 'https://placehold.co/400x300?text=Logitech+G102', 8),
('Razer DeathAdder Essential', 'Thiết kế công thái học, cảm biến 6400 DPI', 490000, 50, 'https://placehold.co/400x300?text=DeathAdder', 8),
('Logitech Pebble Mouse 2 M350s', 'Chuột Bluetooth siêu mỏng, click giảm ồn', 550000, 60, 'https://placehold.co/400x300?text=Pebble+M350s', 8),
-- 9. Màn hình (3 SP)
('LG UltraGear 24GN60R-B 24 inch', 'Màn hình phẳng IPS, 144Hz, 1ms', 3290000, 16, 'https://placehold.co/400x300?text=LG+24GN60R', 9),
('Dell UltraSharp U2424H 24 inch', 'Màn hình đồ hoạ chuẩn màu, tần số 120Hz', 6290000, 12, 'https://placehold.co/400x300?text=Dell+U2424H', 9),
('Samsung Odyssey G5 27 inch', 'Màn hình cong 2K 165Hz chuyên game', 5490000, 10, 'https://placehold.co/400x300?text=Odyssey+G5', 9),
-- 10. Ổ cứng (3 SP)
('SSD Samsung T7 Portable 1TB', 'Ổ cứng di động USB 3.2 tốc độ 1050MB/s', 2590000, 22, 'https://placehold.co/400x300?text=Samsung+T7+1TB', 10),
('SSD Kingston NV2 500GB NVMe', 'Ổ cứng gắn trong PCIe 4.0 M.2 2280', 1050000, 40, 'https://placehold.co/400x300?text=Kingston+NV2', 10),
('HDD WD My Passport 2TB', 'Ổ cứng di động lưu trữ dữ liệu dung lượng lớn', 1950000, 25, 'https://placehold.co/400x300?text=WD+My+Passport', 10),
-- 11. Sạc dự phòng (3 SP)
('Anker PowerCore 10000mAh 22.5W', 'Sạc nhanh hai chiều, thiết kế nhỏ gọn', 590000, 50, 'https://placehold.co/400x300?text=Anker+10000mAh', 11),
('Baseus Airpow 20000mAh 20W', 'Dung lượng lớn, hỗ trợ sạc nhanh PD', 490000, 45, 'https://placehold.co/400x300?text=Baseus+20000mAh', 11),
('Xiaomi Power Bank 10000mAh 22.5W', 'Vỏ nhôm nguyên khối, 3 cổng sạc ra', 420000, 55, 'https://placehold.co/400x300?text=Xiaomi+PowerBank', 11),
-- 12. Cáp & Củ sạc (3 SP)
('Củ sạc Anker Nano 3 30W GaN', 'Chân gập tiện lợi, sạc nhanh iPhone/iPad', 390000, 65, 'https://placehold.co/400x300?text=Anker+Nano+30W', 12),
('Cáp Ugreen USB-C to USB-C 100W 1m', 'Bọc dù chống đứt, hỗ trợ sạc laptop', 150000, 100, 'https://placehold.co/400x300?text=Cap+Ugreen+100W', 12),
('Củ sạc Baseus GaN5 Pro 65W', '2 cổng Type-C + 1 cổng USB-A đa năng', 550000, 40, 'https://placehold.co/400x300?text=Baseus+GaN5+65W', 12),
-- 13. Ốp lưng & Bao da (2 SP)
('Ốp lưng MagSafe iPhone 15 Pro Max', 'Nhựa trong suốt chống ố vàng', 490000, 70, 'https://placehold.co/400x300?text=Op+MagSafe+15PM', 13),
('Bao da Smart Folio iPad Air', 'Gập đa góc độ, tự động tắt mở màn hình', 690000, 30, 'https://placehold.co/400x300?text=Bao+Da+iPad', 13),
-- 14. Thiết bị mạng (2 SP)
('Router Wi-Fi 6 TP-Link Archer AX23', 'Băng tần kép AX1800, hỗ trợ OneMesh', 1090000, 25, 'https://placehold.co/400x300?text=TP-Link+AX23', 14),
('Bộ kích sóng Wi-Fi Xiaomi Pro', 'Tăng cường phủ sóng 300Mbps nhỏ gọn', 250000, 60, 'https://placehold.co/400x300?text=Xiaomi+Repeater', 14),
-- 15. Phụ kiện khác (2 SP)
('Giá đỡ Laptop nhôm hợp kim', 'Điều chỉnh 6 nấc độ cao, tản nhiệt tốt', 290000, 50, 'https://placehold.co/400x300?text=Gia+Do+Laptop', 15),
('Webcam Logitech C270 HD', 'Gọi video HD 720p, micro giảm ồn', 520000, 35, 'https://placehold.co/400x300?text=Logitech+C270', 15);

-- 5 Tài khoản (users) - Mật khẩu tất cả đều là: 123456
INSERT INTO users (username, password, full_name, email, phone, address, role, enabled) VALUES
                                                                                            ('seller1', '$2a$10$uPUNNrC1JUnzBYDZD6AQBe.swyBhdnARmW1.Ctpo737mZZF4hejyS', 'Quản Trị Viên 1', 'seller1@eshop.vn', '0901111111', '12 Nguyễn Văn Bảo, Gò Vấp, TP.HCM', 'ROLE_SELLER', 1),
                                                                                            ('seller2', '$2a$10$uPUNNrC1JUnzBYDZD6AQBe.swyBhdnARmW1.Ctpo737mZZF4hejyS', 'Quản Trị Viên 2', 'seller2@eshop.vn', '0902222222', 'Quận 1, TP.HCM', 'ROLE_SELLER', 1),
                                                                                            ('customer1', '$2a$10$uPUNNrC1JUnzBYDZD6AQBe.swyBhdnARmW1.Ctpo737mZZF4hejyS', 'Nguyễn Văn Mua', 'customer1@gmail.com', '0903333333', 'Quận Bình Thạnh, TP.HCM', 'ROLE_CUSTOMER', 1),
                                                                                            ('customer2', '$2a$10$uPUNNrC1JUnzBYDZD6AQBe.swyBhdnARmW1.Ctpo737mZZF4hejyS', 'Trần Thị Khách', 'customer2@gmail.com', '0904444444', 'TP. Thủ Đức, TP.HCM', 'ROLE_CUSTOMER', 1),
                                                                                            ('locked', '$2a$10$uPUNNrC1JUnzBYDZD6AQBe.swyBhdnARmW1.Ctpo737mZZF4hejyS', 'Tài Khoản Bị Khoá', 'locked@gmail.com', '0905555555', 'Quận 3, TP.HCM', 'ROLE_CUSTOMER', 0);

-- Kiểm tra số lượng bản ghi sau khi tạo
SELECT
    (SELECT COUNT(*) FROM category) AS category_count,
    (SELECT COUNT(*) FROM product) AS product_count,
    (SELECT COUNT(*) FROM users) AS users_count;