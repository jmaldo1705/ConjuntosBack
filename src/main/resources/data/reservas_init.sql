-- Script de inicialización para el módulo de Reservas
-- Base de datos: vecinos_conectados

-- ===================================
-- CREAR TABLAS
-- ===================================

-- Tabla de Zonas Comunes
CREATE TABLE IF NOT EXISTS zonas_comunes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    capacidad INT NOT NULL,
    icono VARCHAR(20),
    color VARCHAR(50),
    tarifa DECIMAL(10, 2) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    requiere_reserva BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Horarios
CREATE TABLE IF NOT EXISTS horarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    zona_comun_id BIGINT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (zona_comun_id) REFERENCES zonas_comunes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de Reservas
CREATE TABLE IF NOT EXISTS reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    zona_comun_id BIGINT NOT NULL,
    horario_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    fecha DATE NOT NULL,
    nombre_evento VARCHAR(200),
    observaciones TEXT,
    estado VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE',
    costo_total DECIMAL(10, 2) NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    fecha_cancelacion DATETIME,
    motivo_cancelacion TEXT,
    FOREIGN KEY (zona_comun_id) REFERENCES zonas_comunes(id),
    FOREIGN KEY (horario_id) REFERENCES horarios(id),
    FOREIGN KEY (usuario_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_fecha (fecha),
    INDEX idx_estado (estado),
    INDEX idx_usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===================================
-- INSERTAR ZONAS COMUNES
-- ===================================

INSERT INTO zonas_comunes (nombre, descripcion, capacidad, icono, color, tarifa, disponible, requiere_reserva) VALUES
('Salón Social', 'Amplio salón perfecto para eventos, reuniones y celebraciones familiares con capacidad para 50 personas. Cuenta con cocina equipada, baño, aire acondicionado y sistema de sonido.', 50, '🏛️', 'from-purple-500 to-pink-500', 120000.00, TRUE, TRUE),

('Piscina', 'Piscina climatizada con área de recreación, zona de descanso y jacuzzi para 20 personas. Incluye vestieres, duchas y área de BBQ pequeña.', 20, '🏊', 'from-blue-500 to-cyan-500', 80000.00, TRUE, TRUE),

('Cancha Múltiple', 'Cancha deportiva adaptada para fútbol, baloncesto, voleibol y otros deportes. Superficie sintética con iluminación nocturna y graderías.', 22, '⚽', 'from-green-500 to-emerald-500', 60000.00, TRUE, TRUE),

('Zona BBQ', 'Área de parrillas con mesas, zona verde y todo lo necesario para asados familiares. Incluye 4 parrillas, mesas con sillas y nevera.', 30, '🔥', 'from-orange-500 to-red-500', 90000.00, TRUE, TRUE),

('Sala de Juntas', 'Sala equipada con tecnología para reuniones corporativas, administrativas y de copropietarios. TV de 65", proyector, pizarra y WiFi de alta velocidad.', 15, '💼', 'from-indigo-500 to-purple-500', 70000.00, TRUE, TRUE),

('Gimnasio', 'Gimnasio completamente equipado con máquinas modernas para rutinas de ejercicio y entrenamiento. Incluye caminadoras, bicicletas, pesas y área de yoga.', 12, '💪', 'from-red-500 to-pink-500', 50000.00, TRUE, TRUE),

('Cancha de Tenis', 'Cancha profesional de tenis con superficie de arcilla sintética, iluminación nocturna y red profesional. Incluye alquiler de raquetas.', 4, '🎾', 'from-yellow-500 to-orange-500', 75000.00, TRUE, TRUE),

('Salón Infantil', 'Espacio diseñado para fiestas infantiles con decoración colorida, juegos inflables y área de entretenimiento para niños. Incluye mesas, sillas infantiles y sonido.', 40, '🎈', 'from-pink-500 to-purple-500', 100000.00, TRUE, TRUE);

-- ===================================
-- INSERTAR HORARIOS PARA CADA ZONA
-- ===================================

-- Horarios para Salón Social (ID: 1)
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(1, 'Mañana', '08:00:00', '12:00:00', 120000.00, TRUE),
(1, 'Tarde', '14:00:00', '18:00:00', 120000.00, TRUE),
(1, 'Noche', '19:00:00', '23:00:00', 150000.00, TRUE),
(1, 'Día Completo', '08:00:00', '23:00:00', 350000.00, TRUE);

-- Horarios para Piscina (ID: 2)
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(2, 'Primera Mañana', '06:00:00', '10:00:00', 80000.00, TRUE),
(2, 'Segunda Mañana', '10:00:00', '14:00:00', 80000.00, TRUE),
(2, 'Tarde', '14:00:00', '18:00:00', 90000.00, TRUE),
(2, 'Noche', '18:00:00', '22:00:00', 100000.00, TRUE);

-- Horarios para Cancha Múltiple (ID: 3)
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(3, 'Mañana', '06:00:00', '12:00:00', 60000.00, TRUE),
(3, 'Tarde', '14:00:00', '18:00:00', 60000.00, TRUE),
(3, 'Noche', '18:00:00', '22:00:00', 70000.00, TRUE);

-- Horarios para Zona BBQ (ID: 4)
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(4, 'Almuerzo', '11:00:00', '15:00:00', 90000.00, TRUE),
(4, 'Tarde', '15:00:00', '19:00:00', 90000.00, TRUE),
(4, 'Noche', '19:00:00', '23:00:00', 100000.00, TRUE);

-- Horarios para Sala de Juntas (ID: 5)
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(5, 'Mañana', '08:00:00', '12:00:00', 70000.00, TRUE),
(5, 'Tarde', '14:00:00', '18:00:00', 70000.00, TRUE),
(5, 'Noche', '18:00:00', '21:00:00', 80000.00, TRUE);

-- Horarios para Gimnasio (ID: 6)
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(6, 'Madrugada', '05:00:00', '08:00:00', 50000.00, TRUE),
(6, 'Mañana', '08:00:00', '12:00:00', 50000.00, TRUE),
(6, 'Tarde', '14:00:00', '18:00:00', 50000.00, TRUE),
(6, 'Noche', '18:00:00', '22:00:00', 60000.00, TRUE);

-- Horarios para Cancha de Tenis (ID: 7)
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(7, 'Mañana Temprano', '06:00:00', '08:00:00', 45000.00, TRUE),
(7, 'Mañana', '08:00:00', '10:00:00', 45000.00, TRUE),
(7, 'Media Mañana', '10:00:00', '12:00:00', 45000.00, TRUE),
(7, 'Tarde', '14:00:00', '16:00:00', 45000.00, TRUE),
(7, 'Media Tarde', '16:00:00', '18:00:00', 45000.00, TRUE),
(7, 'Noche', '18:00:00', '20:00:00', 60000.00, TRUE);

-- Horarios para Salón Infantil (ID: 8)
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(8, 'Mañana', '09:00:00', '13:00:00', 100000.00, TRUE),
(8, 'Tarde', '15:00:00', '19:00:00', 100000.00, TRUE),
(8, 'Día Completo', '09:00:00', '19:00:00', 180000.00, TRUE);

-- ===================================
-- INSERTAR RESERVAS DE EJEMPLO (OPCIONAL)
-- ===================================

-- Nota: Estas reservas son de ejemplo. El usuario_id debe existir en la tabla users.
-- Ajusta las fechas según tus necesidades de prueba.

-- INSERT INTO reservas (zona_comun_id, horario_id, usuario_id, fecha, nombre_evento, observaciones, estado, costo_total) VALUES
-- (1, 3, 1, '2025-12-25', 'Celebración Navidad', 'Reunión familiar navideña', 'CONFIRMADA', 150000.00),
-- (2, 3, 2, '2025-12-31', 'Despedida de año', 'Fiesta familiar en piscina', 'PENDIENTE', 90000.00),
-- (3, 3, 3, '2025-11-15', 'Partido de fútbol', 'Torneo del conjunto', 'CONFIRMADA', 70000.00),
-- (4, 1, 4, '2025-11-20', 'Almuerzo familiar', 'Asado dominical', 'CONFIRMADA', 90000.00);

-- ===================================
-- VERIFICACIÓN
-- ===================================

-- Consulta para verificar zonas comunes creadas
SELECT 'Zonas Comunes creadas:' AS mensaje;
SELECT id, nombre, capacidad, tarifa, disponible FROM zonas_comunes;

-- Consulta para verificar horarios creados
SELECT 'Horarios creados por zona:' AS mensaje;
SELECT 
    zc.nombre AS zona,
    h.nombre AS horario,
    h.hora_inicio,
    h.hora_fin,
    h.precio
FROM horarios h
INNER JOIN zonas_comunes zc ON h.zona_comun_id = zc.id
ORDER BY zc.id, h.hora_inicio;

-- Consulta para contar registros
SELECT 
    (SELECT COUNT(*) FROM zonas_comunes) AS total_zonas,
    (SELECT COUNT(*) FROM horarios) AS total_horarios,
    (SELECT COUNT(*) FROM reservas) AS total_reservas;

COMMIT;
