CREATE TABLE Roles (
    role_id INT IDENTITY(1,1) PRIMARY KEY,
    role_name NVARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Users (
    user_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    email NVARCHAR(100) UNIQUE,
    phone NVARCHAR(20),
    role_id INT NOT NULL,
    is_active BIT DEFAULT 1,
    created_at DATETIME2 DEFAULT SYSDATETIME(),
    CONSTRAINT FK_Users_Roles FOREIGN KEY (role_id) REFERENCES Roles(role_id)
);

CREATE TABLE Employees (
    employee_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    full_name NVARCHAR(100) NOT NULL,
    position NVARCHAR(50),
    salary DECIMAL(12,2),
    hire_date DATE,
    is_active BIT DEFAULT 1,
    CONSTRAINT FK_Employees_Users FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

INSERT INTO Roles (role_name) VALUES ('ADMIN'), ('EMPLOYEE'), ('CUSTOMER');