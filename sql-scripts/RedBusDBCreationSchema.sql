CREATE TABLE user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15)
);

CREATE TABLE bus (
    bus_id INT PRIMARY KEY AUTO_INCREMENT,
    bus_no VARCHAR(10) UNIQUE NOT NULL,
    company_name VARCHAR(50),
    total_seats INT,
    available_seats INT,
    bus_type ENUM('AC', 'Non-AC', 'Sleeper') NOT NULL
);

CREATE TABLE route (
    route_id INT PRIMARY KEY AUTO_INCREMENT,
    source VARCHAR(50) NOT NULL,
    destination VARCHAR(50) NOT NULL
);

CREATE TABLE bus_route (
    bus_route_id INT PRIMARY KEY AUTO_INCREMENT,
    bus_id INT,
    route_id INT,
    direction ENUM('Outbound', 'Inbound') NOT NULL,
    FOREIGN KEY (bus_id) REFERENCES bus(bus_id),
    FOREIGN KEY (route_id) REFERENCES route(route_id)
);

CREATE TABLE booking (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    bus_route_id INT,
    booking_date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (bus_route_id) REFERENCES bus_route(bus_route_id)
);

CREATE TABLE passenger (
    passenger_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT,
    name VARCHAR(50) NOT NULL,
    age INT,
    gender ENUM('Male', 'Female', 'Other'),
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
);

CREATE TABLE payment (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date DATETIME NOT NULL,
    payment_method VARCHAR(50),
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id)
);

CREATE TABLE travel (
    travel_id INT PRIMARY KEY AUTO_INCREMENT,
    passenger_id INT,
    traveled BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (passenger_id) REFERENCES passenger(passenger_id)
);



