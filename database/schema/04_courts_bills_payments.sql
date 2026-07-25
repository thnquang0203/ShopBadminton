CREATE TABLE BadmintonCourts (
    court_id INT IDENTITY(1,1) PRIMARY KEY,
    court_name NVARCHAR(50) NOT NULL,
    court_type NVARCHAR(50),
    price_per_hour DECIMAL(10,2) NOT NULL,
    status NVARCHAR(20) DEFAULT 'AVAILABLE'
);

CREATE TABLE CourtBookings (
    booking_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    court_id INT NOT NULL,
    customer_id BIGINT NOT NULL,
    booking_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status NVARCHAR(20) DEFAULT 'PENDING',
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT FK_Bookings_Courts FOREIGN KEY (court_id) REFERENCES BadmintonCourts(court_id),
    CONSTRAINT FK_Bookings_Customers FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

CREATE TABLE Bills (
    bill_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    total_amount DECIMAL(14,2) DEFAULT 0,
    status NVARCHAR(20) DEFAULT 'UNPAID',
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT FK_Bills_Customers FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),
    CONSTRAINT FK_Bills_Employees FOREIGN KEY (employee_id) REFERENCES Employees(employee_id)
);

CREATE TABLE BillDetails (
    bill_detail_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    bill_id BIGINT NOT NULL,
    product_id BIGINT NULL,
    booking_id BIGINT NULL,
    quantity INT DEFAULT 1,
    unit_price DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(14,2) NOT NULL,
    CONSTRAINT FK_BillDetails_Bills FOREIGN KEY (bill_id) REFERENCES Bills(bill_id),
    CONSTRAINT FK_BillDetails_Products FOREIGN KEY (product_id) REFERENCES Products(product_id),
    CONSTRAINT FK_BillDetails_Bookings FOREIGN KEY (booking_id) REFERENCES CourtBookings(booking_id),
    CONSTRAINT CHK_BillDetails_OneType CHECK (
        (product_id IS NOT NULL AND booking_id IS NULL) OR
        (product_id IS NULL AND booking_id IS NOT NULL)
    )
);

CREATE TABLE Payments (
    payment_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    bill_id BIGINT NOT NULL,
    amount DECIMAL(14,2) NOT NULL,
    payment_method NVARCHAR(20),
    paid_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT FK_Payments_Bills FOREIGN KEY (bill_id) REFERENCES Bills(bill_id)
);