CREATE TABLE Suppliers (
    supplier_id INT IDENTITY(1,1) PRIMARY KEY,
    supplier_name NVARCHAR(150) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100),
    address NVARCHAR(255),
    is_active BIT DEFAULT 1
);

CREATE TABLE PurchaseOrders (
    purchase_order_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    supplier_id INT NOT NULL,
    employee_id BIGINT NOT NULL,
    order_date DATETIME2 DEFAULT SYSDATETIME(),
    total_amount DECIMAL(14,2) DEFAULT 0,
    status NVARCHAR(20) DEFAULT 'COMPLETED',
    CONSTRAINT FK_PurchaseOrders_Suppliers FOREIGN KEY (supplier_id) REFERENCES Suppliers(supplier_id),
    CONSTRAINT FK_PurchaseOrders_Employees FOREIGN KEY (employee_id) REFERENCES Employees(employee_id)
);

CREATE TABLE PurchaseDetails (
    purchase_detail_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    purchase_order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    CONSTRAINT FK_PurchaseDetails_Orders FOREIGN KEY (purchase_order_id) REFERENCES PurchaseOrders(purchase_order_id),
    CONSTRAINT FK_PurchaseDetails_Products FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

CREATE TABLE Inventory (
    inventory_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id BIGINT NOT NULL UNIQUE,
    quantity INT DEFAULT 0,
    min_quantity INT DEFAULT 5,
    updated_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT FK_Inventory_Products FOREIGN KEY (product_id) REFERENCES Products(product_id)
);