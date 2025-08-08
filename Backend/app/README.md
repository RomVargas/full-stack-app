# Backend - Gestión de Usuarios

## Descripción
Aplicación Spring Boot para la gestión de usuarios con base de datos H2 persistente.

## Características
- ✅ Base de datos H2 con persistencia de datos
- ✅ API REST completa para CRUD de usuarios
- ✅ Validaciones de datos
- ✅ Manejo de excepciones global
- ✅ CORS configurado para frontend Angular
- ✅ Documentación de endpoints

## Tecnologías
- **Spring Boot 3.4.9**
- **Spring Data JPA**
- **H2 Database**
- **Java 21**
- **Maven**

## Configuración de la Base de Datos

### H2 Database
- **URL**: `jdbc:h2:file:./data/usersdb`
- **Usuario**: `sa`
- **Contraseña**: `password`
- **Consola H2**: `http://localhost:8080/api/h2-console`

### Estructura de la Tabla
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);
```

## Endpoints de la API

### Base URL
```
http://localhost:8080/api
```

### Endpoints Disponibles

#### 1. Obtener todos los usuarios
```
GET /users
```

#### 2. Obtener usuario por ID
```
GET /users/{id}
```

#### 3. Crear nuevo usuario
```
POST /users
Content-Type: application/json

{
    "firstName": "Juan",
    "lastName": "Pérez",
    "email": "juan.perez@example.com"
}
```

#### 4. Actualizar usuario
```
PUT /users/{id}
Content-Type: application/json

{
    "firstName": "Juan",
    "lastName": "Pérez",
    "email": "juan.perez@example.com"
}
```

#### 5. Eliminar usuario
```
DELETE /users/{id}
```

#### 6. Verificar salud de la API
```
GET /users/health
```

## Ejecutar la Aplicación

### Prerrequisitos
- Java 21 o superior
- Maven 3.6 o superior

### Comandos
```bash
# Compilar y ejecutar
mvn spring-boot:run

# O compilar primero y luego ejecutar
mvn clean compile
mvn spring-boot:run
```

### Acceso a la Aplicación
- **API**: http://localhost:8080/api
- **Consola H2**: http://localhost:8080/api/h2-console
- **Frontend**: http://localhost:4200

## Validaciones

### Campos Requeridos
- `firstName`: Obligatorio, 2-50 caracteres
- `lastName`: Obligatorio, 2-50 caracteres
- `email`: Obligatorio, formato válido, único

### Validaciones Automáticas
- ✅ Formato de email válido
- ✅ Email único en la base de datos
- ✅ Campos no vacíos
- ✅ Longitud de campos

## Manejo de Errores

### Códigos de Respuesta
- `200 OK`: Operación exitosa
- `201 Created`: Usuario creado exitosamente
- `400 Bad Request`: Datos inválidos
- `404 Not Found`: Usuario no encontrado
- `500 Internal Server Error`: Error del servidor

### Ejemplos de Respuestas de Error
```json
{
    "error": "Ya existe un usuario con el email: juan.perez@example.com"
}
```

```json
{
    "firstName": "El nombre es obligatorio",
    "email": "El formato del email no es válido"
}
```

## Datos de Inicialización

La aplicación incluye 5 usuarios de ejemplo que se crean automáticamente:
1. Juan Pérez - juan.perez@example.com
2. María García - maria.garcia@example.com
3. Carlos López - carlos.lopez@example.com
4. Ana Martínez - ana.martinez@example.com
5. Luis Rodríguez - luis.rodriguez@example.com

## Persistencia de Datos

Los datos se almacenan en el archivo `./data/usersdb.mv.db` y persisten entre reinicios de la aplicación.

## Configuración de CORS

La aplicación está configurada para permitir peticiones desde:
- **Origen**: http://localhost:4200 (Frontend Angular)
- **Métodos**: GET, POST, PUT, DELETE, OPTIONS
- **Headers**: Todos los headers

## Estructura del Proyecto

```
src/main/java/com/itj/app/
├── AppApplication.java          # Clase principal
├── config/
│   └── WebConfig.java          # Configuración CORS
├── controller/
│   └── UserController.java     # Controlador REST
├── exception/
│   ├── UserException.java      # Excepción personalizada
│   └── GlobalExceptionHandler.java # Manejador global
├── model/
│   └── User.java              # Entidad JPA
├── repository/
│   └── UserRepository.java    # Repositorio JPA
└── service/
    └── UserService.java       # Lógica de negocio
```

## Desarrollo

### Agregar Nuevos Endpoints
1. Crear método en `UserController`
2. Implementar lógica en `UserService`
3. Agregar validaciones si es necesario
4. Probar con Postman o similar

### Modificar Validaciones
1. Editar anotaciones en `User.java`
2. Actualizar `UserService.validateUserFields()`
3. Probar con datos inválidos

### Cambiar Base de Datos
1. Modificar `application.properties`
2. Actualizar dependencias en `pom.xml`
3. Migrar datos si es necesario

