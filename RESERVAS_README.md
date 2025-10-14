# 📋 Guía de Configuración - Sistema de Reservas

## ✅ Lo que ya está hecho:

### Backend (Java Spring Boot):
- ✅ Entidades JPA (ZonaComun, Horario, Reserva)
- ✅ DTOs para transferencia de datos
- ✅ Repositories con queries optimizadas
- ✅ Service con lógica de negocio
- ✅ Controller con endpoints REST
- ✅ Scripts SQL de inicialización

### Frontend (Angular):
- ✅ Servicio actualizado para consumir API real
- ✅ Interfaces TypeScript actualizadas

## 🚀 Pasos para Activar el Sistema:

### 1. Reiniciar el Backend

Presiona `Ctrl+C` en el terminal de Java y luego ejecuta:

```powershell
cd c:\Users\Jonat\Documents\VecinosConectados\ConjuntosBack
.\gradlew.bat bootRun
```

### 2. Ejecutar el Script SQL

Tienes dos opciones:

#### Opción A: Script Completo (Crea tablas + datos)
```sql
-- Ejecutar en MySQL Workbench o tu cliente SQL favorito
source c:/Users/Jonat/Documents/VecinosConectados/ConjuntosBack/src/main/resources/data/reservas_init.sql
```

#### Opción B: Script Simple (Solo datos - JPA crea tablas)

**Paso 1:** Cambiar temporalmente el `ddl-auto` en `application.yml`:
```yaml
jpa:
  hibernate:
    ddl-auto: update  # Cambiar de 'none' a 'update'
```

**Paso 2:** Reiniciar el backend (creará las tablas automáticamente)

**Paso 3:** Ejecutar el script de datos:
```sql
source c:/Users/Jonat/Documents/VecinosConectados/ConjuntosBack/src/main/resources/data/reservas_data.sql
```

**Paso 4:** Volver a poner `ddl-auto: none` en `application.yml`

### 3. Verificar las Tablas

Ejecuta en MySQL:

```sql
USE vecinos_conectados;

-- Ver zonas comunes
SELECT * FROM zonas_comunes;

-- Ver horarios por zona
SELECT 
    zc.nombre AS zona,
    h.nombre AS horario,
    h.hora_inicio,
    h.hora_fin,
    h.precio
FROM horarios h
INNER JOIN zonas_comunes zc ON h.zona_comun_id = zc.id
ORDER BY zc.id, h.hora_inicio;
```

## 📡 Endpoints Disponibles:

### Zonas Comunes:
- `GET http://localhost:8080/api/reservas/zonas-comunes` - Todas las zonas
- `GET http://localhost:8080/api/reservas/zonas-comunes/{id}` - Una zona específica

### Reservas del Usuario:
- `GET http://localhost:8080/api/reservas/mis-reservas` - Mis reservas
- `GET http://localhost:8080/api/reservas/proximas` - Próximas reservas
- `GET http://localhost:8080/api/reservas/por-fecha?fechaInicio=2025-01-01&fechaFin=2025-12-31` - Por rango

### Estadísticas:
- `GET http://localhost:8080/api/reservas/estadisticas` - Estadísticas generales

### Operaciones:
- `POST http://localhost:8080/api/reservas` - Crear reserva
- `PUT http://localhost:8080/api/reservas/{id}/confirmar` - Confirmar
- `PUT http://localhost:8080/api/reservas/{id}/cancelar?motivo=texto` - Cancelar
- `DELETE http://localhost:8080/api/reservas/{id}` - Eliminar

## 🧪 Probar desde Postman:

### 1. Obtener Zonas Comunes:
```
GET http://localhost:8080/api/reservas/zonas-comunes
Headers:
  Authorization: Bearer {tu_token_jwt}
```

### 2. Crear una Reserva:
```
POST http://localhost:8080/api/reservas
Headers:
  Authorization: Bearer {tu_token_jwt}
  Content-Type: application/json
Body:
{
  "zonaId": 1,
  "horarioId": 3,
  "fecha": "2025-12-25",
  "nombreEvento": "Celebración Navidad",
  "observaciones": "Reunión familiar"
}
```

## 🎨 Datos Insertados:

### 8 Zonas Comunes:
1. Salón Social (4 horarios) - $120,000 - $350,000
2. Piscina (4 horarios) - $80,000 - $100,000
3. Cancha Múltiple (3 horarios) - $60,000 - $70,000
4. Zona BBQ (3 horarios) - $90,000 - $100,000
5. Sala de Juntas (3 horarios) - $70,000 - $80,000
6. Gimnasio (4 horarios) - $50,000 - $60,000
7. Cancha de Tenis (6 horarios) - $45,000 - $60,000
8. Salón Infantil (3 horarios) - $100,000 - $180,000

### Total: 30 Horarios diferentes

## ⚠️ Notas Importantes:

1. **Autenticación Requerida**: Todos los endpoints requieren un token JWT válido
2. **Usuario Actual**: El sistema obtiene el usuario del contexto de seguridad de Spring
3. **Validaciones**:
   - No se pueden hacer reservas en fechas pasadas
   - No se permite duplicar reservas (misma zona + horario + fecha)
   - Solo el dueño puede cancelar/eliminar sus reservas

## 🐛 Solución de Problemas:

### Error: "Table doesn't exist"
- Ejecuta el script `reservas_init.sql` o cambia `ddl-auto` a `update`

### Error: "Usuario no encontrado"
- Asegúrate de estar autenticado y tener un token JWT válido
- Verifica que el usuario exista en la tabla `users`

### Error 403 Forbidden:
- Verifica el token JWT en el header Authorization
- Asegúrate de que el endpoint esté permitido en SecurityConfig

### Frontend no carga datos:
- Verifica que el backend esté corriendo en el puerto 8080
- Revisa la consola del navegador (F12) para ver errores
- Verifica que estés autenticado en el frontend

## ✨ Siguientes Pasos:

1. ✅ Reiniciar backend
2. ✅ Ejecutar script SQL
3. ✅ Probar endpoints con Postman
4. ✅ Verificar que el frontend cargue las zonas comunes
5. ✅ Crear una reserva de prueba desde el frontend
6. ✅ Verificar el calendario de reservas

¡Todo listo! 🎉
