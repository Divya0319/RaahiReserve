INSERT INTO bus (bus_no, company_name, total_seats, available_seats, bus_type)
VALUES
    ('KA01AB1234', 'ABC Travels', 50, 45, 'AC'),
    ('KA01CD5678', 'XYZ Travels', 40, 40, 'NON_AC');

INSERT INTO route (source, destination)
VALUES
    ('Bangalore', 'Mysore'),
    ('Bangalore', 'Chennai'),
    ('Mysore', 'Chennai');

INSERT INTO bus_route (bus_id, route_id, direction)
VALUES
    (1, 1, 'UP'),
    (1, 2, 'DOWN'),
    (2, 1, 'UP'),
    (2, 3, 'DOWN');

INSERT INTO user (user_name, password, email, phone_number)
VALUES
    ('divya', 'divya123', 'divya@gmail.com', '9898978787'),
    ('guruji', 'guruji123', 'guruji@gmail.com', '8989878787');

INSERT INTO booking (user_id, bus_route_id, booking_date, payment_id)
VALUES
    (1, 1, '2023-08-01 10:00:00', NULL),
    (2, 2, '2023-08-02 11:00:00', NULL);

INSERT INTO payment (booking_id, amount, payment_method, payment_status)
VALUES
    (1, 500.00, 'CREDIT_CARD', 'COMPLETED'),
    (2, 300.00, 'DEBIT_CARD', 'PENDING');

UPDATE booking SET payment_id = 1 WHERE booking_id = 1;
UPDATE booking SET payment_id = 2 WHERE booking_id = 2;

INSERT INTO passenger (name, age, gender, bus_seat_id)
VALUES
    ('Kavya', 30, 'FEMALE', NULL),
    ('Ronit', 25, 'MALE', NULL);

INSERT INTO bus_seat (bus_id, seat_number)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 2);

UPDATE passenger SET bus_seat_id = 1 WHERE passenger_id = 1;
UPDATE passenger SET bus_seat_id = 2 WHERE passenger_id = 2;

INSERT INTO travel (traveled)
VALUES
    (FALSE),
    (TRUE);

INSERT INTO passenger_travel (travel_id, passenger_id)
VALUES
    (1, 1),
    (2, 2);
