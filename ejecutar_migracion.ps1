# Script para ejecutar la migración de base de datos
# Ajusta la ruta de MySQL si es necesario

$mysqlPath = "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
$dbName = "vecinos_conectados"
$username = "root"
$scriptPath = "src\main\resources\data\add_audit_fields_to_reservas.sql"

Write-Host "Ejecutando migración de base de datos..." -ForegroundColor Green

if (Test-Path $mysqlPath) {
    & $mysqlPath -u $username -p $dbName < $scriptPath
    Write-Host "Migración ejecutada exitosamente!" -ForegroundColor Green
} else {
    Write-Host "MySQL no encontrado en: $mysqlPath" -ForegroundColor Red
    Write-Host "Por favor, ejecuta el script SQL manualmente usando MySQL Workbench o phpMyAdmin" -ForegroundColor Yellow
    Write-Host "Archivo SQL: $scriptPath" -ForegroundColor Cyan
}

Read-Host "Presiona Enter para continuar..."
