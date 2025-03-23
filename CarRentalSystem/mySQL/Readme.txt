My SQL Read Me File

Step 1: Install MySQL Server and MySQL WorkBench

Step 2: Log in to My SQL workbench (UserId = root; password=password)

step 3: Create Database

******************START OF ONE TIME ACTIVITY*********************************
CREATE DATABASE IF NOT EXISTS carrental;
USE carrental;


-- 2. Creation of all required Tables

-- Create User Table
CREATE TABLE IF NOT EXISTS CR_User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Staff', 'Customer') NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Additional Profile Fields
    first_name VARCHAR(50) DEFAULT NULL,
    last_name VARCHAR(50) DEFAULT NULL,
    address1 VARCHAR(100) DEFAULT NULL,
    address2 VARCHAR(100) DEFAULT NULL,
    city VARCHAR(50) DEFAULT NULL,
    province VARCHAR(50) DEFAULT NULL,
    postal_code VARCHAR(20) DEFAULT NULL,
    country VARCHAR(50) DEFAULT NULL,

    -- Payment Information Fields
    card_name VARCHAR(100) DEFAULT NULL,
    card_number VARCHAR(20) DEFAULT NULL,
    exp_month VARCHAR(5) DEFAULT NULL,
    exp_year VARCHAR(5) DEFAULT NULL,
    security_code VARCHAR(10) DEFAULT NULL,
    card_postal VARCHAR(20) DEFAULT NULL,
    card_country VARCHAR(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Create Car Inventory Table
CREATE TABLE IF NOT EXISTS CR_Inventory (
    car_id INT AUTO_INCREMENT PRIMARY KEY,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    price_per_day DECIMAL(10,2) NOT NULL,
    availability BOOLEAN DEFAULT TRUE
);

-- Create Booking Table
CREATE TABLE IF NOT EXISTS CR_Booking (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    car_id INT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status ENUM('Pending', 'Confirmed', 'Cancelled', 'Completed') DEFAULT 'Pending',
    FOREIGN KEY (user_id) REFERENCES CR_User(user_id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES CR_Inventory(car_id) ON DELETE CASCADE
);

-- Create Payment Table
CREATE TABLE IF NOT EXISTS CR_Payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT,
    amount DECIMAL(10,2) NOT NULL,
    payment_status ENUM('Paid', 'Pending', 'Failed') DEFAULT 'Pending',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES CR_Booking(booking_id) ON DELETE CASCADE
);

-- Create Maintenance Table
CREATE TABLE IF NOT EXISTS CR_Maintenance (
    maintenance_id INT AUTO_INCREMENT PRIMARY KEY,
    car_id INT,
    maintenance_date DATE NOT NULL,
    details TEXT NOT NULL,
    FOREIGN KEY (car_id) REFERENCES CR_Inventory(car_id) ON DELETE CASCADE
);

******************END OF ONE TIME ACTIVITY*********************************


Sample Data to Insert 

-- 3. Insert records with Admin, Customer, and Staff Roles
INSERT INTO CR_User (username, password, role, email) VALUES
('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Admin', 'admin@carrental.com'),
('staff', '1562206543da764123c21bd524674f0a8aaf49c8a89744c97352fe677f7e4006', 'Staff', 'staff1@carrental.com'),
('customer', 'b6c45863875e34487ca3c155ed145efe12a74581e27befec5aa661b8ee8ca6dd', 'Customer', 'customer1@carrental.com');

-- 4. Insert records for Car Inventory
INSERT INTO CR_Inventory (make, model, year, price_per_day, availability) VALUES
('Toyota', 'Camry', 2020, 50.00, TRUE),
('Honda', 'Civic', 2019, 45.00, TRUE),
('Ford', 'Focus', 2021, 55.00, TRUE);

-- 5. Insert records for Booking Table
INSERT INTO CR_Booking (user_id, car_id, start_date, end_date, status) VALUES
(3, 1, '2024-03-10', '2024-03-15', 'Confirmed'),
(3, 2, '2024-03-20', '2024-03-25', 'Pending');

-- 6. Insert records for Payment Table
INSERT INTO CR_Payment (booking_id, amount, payment_status) VALUES
(1, 250.00, 'Paid'),
(2, 225.00, 'Pending');

-- 7. Insert records for Maintenance Table
INSERT INTO CR_Maintenance (car_id, maintenance_date, details) VALUES
(1, '2024-03-18', 'Oil Change and Tire Rotation'),
(2, '2024-03-22', 'Brake Inspection and Engine Tune-up');

-- 8. Insert records for Maintenance Table
SELECT * FROM carrental.cr_booking;
SELECT * FROM carrental.cr_inventory;
SELECT * FROM carrental.cr_maintenance;
SELECT * FROM carrental.cr_payment;
SELECT * FROM carrental.cr_user;

