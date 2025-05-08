-- Ensure table exists (JPA usually handles this if ddl-auto is set)
-- Insert test users (with BCrypt-hashed passwords)

INSERT INTO users (username, password, role) VALUES
('agent007', '$2a$10$Q9eV2kh5Dlttc2V4kI9LRe4Ev.5jXRphI6FE/ODG0Z.JPV1eHmt5W', 'USER'), -- password: Test@123
('admin', '$2a$10$ml03ENvpKYJ6AxyqZzlePuxAV6us3MC/mvZ3zSGZcK9Z6gAYU5Dsy', 'ADMIN'), -- password: Admin@123
('testuser', '$2a$10$yT1FC4DRYZDkhw2SPRx5ROyt5BoRwh7PbCS6GzSGKQfppFErln28K', 'USER') -- password: User@123
ON CONFLICT (username) DO NOTHING;