INSERT INTO training_room (name, details, capacity)
VALUES
    ('Room A', 'Main training room for group classes', 20),
    ('Room B', 'Small room for yoga and pilates', 12),
    ('Outdoor Area', 'Outdoor functional training space', 25);

INSERT INTO trainer (first_name, last_name, phone_number, specialization)
VALUES
    ('Giorgos', 'Papadopoulos', '6900000001', 'CROSSFIT'),
    ('Maria', 'Nikolaou', '6900000002', 'YOGA'),
    ('Nikos', 'Dimitriou', '6900000003', 'FUNCTIONAL');

INSERT INTO training_type (name, description, duration)
VALUES
    ('CrossFit', 'High intensity strength and conditioning class', 60),
    ('Yoga', 'Mobility, flexibility and breathing class', 45),
    ('Functional Training', 'Full body functional workout', 50);