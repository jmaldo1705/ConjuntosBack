-- Agregar campos de auditoría a la tabla reservas
-- Verificar si las columnas no existen antes de agregarlas

SET @dbname = 'vecinos_conectados';
SET @tablename = 'reservas';
SET @columnname1 = 'usuario_creacion';
SET @columnname2 = 'usuario_modificacion';

-- Agregar usuario_creacion si no existe
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = @dbname
    AND TABLE_NAME = @tablename
    AND COLUMN_NAME = @columnname1
  ) > 0,
  'SELECT 1',
  CONCAT('ALTER TABLE ', @tablename, ' ADD COLUMN ', @columnname1, ' VARCHAR(100) AFTER motivo_cancelacion')
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- Agregar usuario_modificacion si no existe
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = @dbname
    AND TABLE_NAME = @tablename
    AND COLUMN_NAME = @columnname2
  ) > 0,
  'SELECT 1',
  CONCAT('ALTER TABLE ', @tablename, ' ADD COLUMN ', @columnname2, ' VARCHAR(100) AFTER usuario_creacion')
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- Actualizar registros existentes con valores por defecto
UPDATE reservas 
SET usuario_creacion = COALESCE(usuario_creacion, 'sistema'), 
    usuario_modificacion = COALESCE(usuario_modificacion, 'sistema') 
WHERE usuario_creacion IS NULL OR usuario_modificacion IS NULL;
