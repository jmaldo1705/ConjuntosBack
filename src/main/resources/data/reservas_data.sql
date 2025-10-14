-- Script simplificado solo con inserts (asumiendo que las tablas ya existen por JPA)
-- Ejecutar después de iniciar el backend por primera vez

USE vecinos_conectados;

-- Limpiar datos previos (opcional)
-- DELETE FROM reservas;
-- DELETE FROM horarios;
-- DELETE FROM zonas_comunes;

-- ===================================
-- ZONAS COMUNES
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
-- HORARIOS
-- ===================================

-- Salón Social
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(1, 'Mañana', '08:00:00', '12:00:00', 120000.00, TRUE),
(1, 'Tarde', '14:00:00', '18:00:00', 120000.00, TRUE),
(1, 'Noche', '19:00:00', '23:00:00', 150000.00, TRUE),
(1, 'Día Completo', '08:00:00', '23:00:00', 350000.00, TRUE);

-- Piscina
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(2, 'Primera Mañana', '06:00:00', '10:00:00', 80000.00, TRUE),
(2, 'Segunda Mañana', '10:00:00', '14:00:00', 80000.00, TRUE),
(2, 'Tarde', '14:00:00', '18:00:00', 90000.00, TRUE),
(2, 'Noche', '18:00:00', '22:00:00', 100000.00, TRUE);

-- Cancha Múltiple
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(3, 'Mañana', '06:00:00', '12:00:00', 60000.00, TRUE),
(3, 'Tarde', '14:00:00', '18:00:00', 60000.00, TRUE),
(3, 'Noche', '18:00:00', '22:00:00', 70000.00, TRUE);

-- Zona BBQ
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(4, 'Almuerzo', '11:00:00', '15:00:00', 90000.00, TRUE),
(4, 'Tarde', '15:00:00', '19:00:00', 90000.00, TRUE),
(4, 'Noche', '19:00:00', '23:00:00', 100000.00, TRUE);

-- Sala de Juntas
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(5, 'Mañana', '08:00:00', '12:00:00', 70000.00, TRUE),
(5, 'Tarde', '14:00:00', '18:00:00', 70000.00, TRUE),
(5, 'Noche', '18:00:00', '21:00:00', 80000.00, TRUE);

-- Gimnasio
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(6, 'Madrugada', '05:00:00', '08:00:00', 50000.00, TRUE),
(6, 'Mañana', '08:00:00', '12:00:00', 50000.00, TRUE),
(6, 'Tarde', '14:00:00', '18:00:00', 50000.00, TRUE),
(6, 'Noche', '18:00:00', '22:00:00', 60000.00, TRUE);

-- Cancha de Tenis
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(7, 'Mañana Temprano', '06:00:00', '08:00:00', 45000.00, TRUE),
(7, 'Mañana', '08:00:00', '10:00:00', 45000.00, TRUE),
(7, 'Media Mañana', '10:00:00', '12:00:00', 45000.00, TRUE),
(7, 'Tarde', '14:00:00', '16:00:00', 45000.00, TRUE),
(7, 'Media Tarde', '16:00:00', '18:00:00', 45000.00, TRUE),
(7, 'Noche', '18:00:00', '20:00:00', 60000.00, TRUE);

-- Salón Infantil
INSERT INTO horarios (zona_comun_id, nombre, hora_inicio, hora_fin, precio, disponible) VALUES
(8, 'Mañana', '09:00:00', '13:00:00', 100000.00, TRUE),
(8, 'Tarde', '15:00:00', '19:00:00', 100000.00, TRUE),
(8, 'Día Completo', '09:00:00', '19:00:00', 180000.00, TRUE);

-- Verificar inserción
SELECT COUNT(*) as zonas FROM zonas_comunes;
SELECT COUNT(*) as horarios FROM horarios;
