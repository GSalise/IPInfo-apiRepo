-- Insert test users with BCrypt hashed passwords
-- Password for all users is: "password123"
-- Hash generated using BCrypt with strength 12

INSERT INTO users (email, password_hash, is_active, created_at) VALUES
('test@example.com', '$2a$12$KZW0cYDfetfqJjikzsspoOYO1OXG25i5TwbzulwvZRF6EYtwGogJ6', TRUE, NOW()),
('admin@example.com', '$2a$12$KZW0cYDfetfqJjikzsspoOYO1OXG25i5TwbzulwvZRF6EYtwGogJ6', TRUE, NOW()),
('user@example.com', '$2a$12$KZW0cYDfetfqJjikzsspoOYO1OXG25i5TwbzulwvZRF6EYtwGogJ6', TRUE, NOW());