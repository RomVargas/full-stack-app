-- Inicialización de datos para la tabla users
-- Estos datos se insertarán automáticamente al iniciar la aplicación

INSERT INTO users (first_name, last_name, email) VALUES 
('Juan', 'Pérez', 'juan.perez@example.com'),
('María', 'García', 'maria.garcia@example.com'),
('Carlos', 'López', 'carlos.lopez@example.com'),
('Ana', 'Martínez', 'ana.martinez@example.com'),
('Luis', 'Rodríguez', 'luis.rodriguez@example.com')
ON DUPLICATE KEY UPDATE first_name = VALUES(first_name), last_name = VALUES(last_name);
