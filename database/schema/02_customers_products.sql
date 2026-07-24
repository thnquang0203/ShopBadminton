CREATE TABLE Customers (
    customer_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NULL UNIQUE,
    full_name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100),
    address NVARCHAR(255),
    is_active BIT DEFAULT 1,
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT FK_Customers_Users FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Categories (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Brands (
    brand_id INT IDENTITY(1,1) PRIMARY KEY,
    brand_name NVARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Products (
    product_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_name NVARCHAR(150) NOT NULL,
    description NVARCHAR(MAX),
    price DECIMAL(12,2) NOT NULL,
    category_id INT NOT NULL,
    brand_id INT NOT NULL,
    is_active BIT DEFAULT 1,
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT FK_Products_Categories FOREIGN KEY (category_id) REFERENCES Categories(category_id),
    CONSTRAINT FK_Products_Brands FOREIGN KEY (brand_id) REFERENCES Brands(brand_id)
);

CREATE TABLE ProductImages (
    image_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id BIGINT NOT NULL,
    image_url NVARCHAR(500) NOT NULL,
    is_thumbnail BIT DEFAULT 0,
    CONSTRAINT FK_ProductImages_Products FOREIGN KEY (product_id) REFERENCES Products(product_id)
);