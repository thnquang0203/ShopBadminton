-- Users mẫu (password đã hash BCrypt cho "123456")
INSERT INTO Users (username, password, email, role_id) VALUES
('admin', '$2a$10$abcdefghijklmnopqrstuv', 'admin@shopbadminton.com', 1),
('nhanvien1', '$2a$10$abcdefghijklmnopqrstuv', 'nv1@shopbadminton.com', 2),
('khachhang1', '$2a$10$abcdefghijklmnopqrstuv', 'kh1@gmail.com', 3);

INSERT INTO Employees (user_id, full_name, position, salary, hire_date) VALUES
(1, N'Nguyễn Văn Admin', N'Quản lý', 15000000, '2026-01-01'),
(2, N'Trần Thị Nhân Viên', N'Nhân viên bán hàng', 8000000, '2026-02-01');

INSERT INTO Customers (user_id, full_name, phone, email) VALUES
(3, N'Lê Văn Khách', '0901234567', 'kh1@gmail.com');

INSERT INTO Categories (category_name) VALUES (N'Vợt cầu lông'), (N'Giày cầu lông'), (N'Phụ kiện');
INSERT INTO Brands (brand_name) VALUES ('Yonex'), ('Lining'), ('Victor');

INSERT INTO Products (product_name, description, price, category_id, brand_id) VALUES
(N'Vợt Yonex Astrox 100', N'Vợt cầu lông cao cấp', 3500000, 1, 1),
(N'Giày Lining Ranger', N'Giày cầu lông chuyên nghiệp', 1200000, 2, 2);

INSERT INTO Inventory (product_id, quantity, min_quantity) VALUES
(1, 20, 5), (2, 15, 5);

INSERT INTO BadmintonCourts (court_name, court_type, price_per_hour, status) VALUES
(N'Sân 1', N'Trong nhà', 100000, 'AVAILABLE'),
(N'Sân 2', N'Trong nhà', 100000, 'AVAILABLE');

INSERT INTO Suppliers (supplier_name, phone, email) VALUES
(N'Công ty TNHH Cầu Lông ABC', '0281234567', 'abc@supplier.com');