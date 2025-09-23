-- Test data for H2 database
-- Password: 1234 (BCrypt hash generated with Spring Security BCryptPasswordEncoder)
INSERT INTO users (username, password, role) VALUES ('localUnitTestUser', '$2a$10$FklhUDC6/PmgNL9ssiNfHeDt3HW.OzXzMI09z6.FqkDkEt5zGLeb.', 'ADMIN');