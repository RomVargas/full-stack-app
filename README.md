# 🚀 Full Stack App - Spring Boot + Angular

Aplicación full-stack moderna construida con Spring Boot (Backend) y Angular (Frontend), desplegable tanto localmente como en la nube.

## 📋 Prerrequisitos

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado y ejecutándose
- [Git](https://git-scm.com/) instalado
- Mínimo 4GB de RAM disponible

## 🐳 Despliegue Local con Docker Compose

### 1. Clonar el Repositorio
```bash
git clone https://github.com/RomVargas/full-stack-app.git
cd full-stack-app
```

### 2. Verificar Configuración

#### Backend (Spring Boot)
- **Puerto:** 8080
- **Base de datos:** H2 (en memoria)
- **API Base URL:** `http://localhost:8080/api`
- **H2 Console:** `http://localhost:8080/api/h2-console`

#### Frontend (Angular)
- **Puerto:** 4200 (mapeado desde 80 en el contenedor)
- **URL de desarrollo:** `http://localhost:4200`
- **API Endpoint:** Configurado para `http://localhost:8080/api`

### 3. Desplegar con Docker Compose

```bash
# Construir e iniciar todos los servicios
docker-compose up --build

# Para ejecutar en segundo plano
docker-compose up --build -d

# Para ver logs en tiempo real
docker-compose logs -f
```

### 4. Acceder a la Aplicación

Una vez que los contenedores estén ejecutándose:

- **🌐 Frontend:** http://localhost:4200
- **🔧 Backend API:** http://localhost:8080/api
- **🗄️ H2 Database Console:** http://localhost:8080/api/h2-console
  - **JDBC URL:** `jdbc:h2:mem:usersdb`
  - **Username:** `sa`
  - **Password:** (dejar vacío)

### 5. Verificar Funcionamiento

#### Backend
```bash
# Verificar que el backend esté funcionando
curl http://localhost:8080/api/users/

# Respuesta esperada:
# "API de usuarios funcionando correctamente"
```

#### Frontend
- Abrir http://localhost:4200 en el navegador
- Deberías ver la interfaz de gestión de usuarios
- Probar crear, editar y eliminar usuarios

## 🔧 Comandos Útiles

### Gestión de Contenedores
```bash
# Iniciar servicios
docker-compose up

# Iniciar en segundo plano
docker-compose up -d

# Detener servicios
docker-compose down

# Reconstruir e iniciar
docker-compose up --build

# Ver logs de un servicio específico
docker-compose logs -f backend
docker-compose logs -f frontend

# Ver estado de los servicios
docker-compose ps
```

### Limpieza y Mantenimiento
```bash
# Detener y eliminar contenedores, redes y volúmenes
docker-compose down -v

# Eliminar imágenes no utilizadas
docker system prune -a

# Limpiar todo (¡CUIDADO!)
docker system prune -a --volumes
```

### Desarrollo
```bash
# Ejecutar solo el backend localmente
cd Backend/app
./mvnw spring-boot:run

# Ejecutar solo el frontend localmente
cd Frontend/app
npm install
npm start
```

## 🐛 Troubleshooting

### Problemas Comunes

#### Puerto 8080 ocupado
```bash
# Verificar qué está usando el puerto
lsof -i :8080

# Matar el proceso si es necesario
kill -9 <PID>
```

#### Puerto 4200 ocupado
```bash
# Verificar qué está usando el puerto
lsof -i :4200

# O cambiar el puerto en docker-compose.yml
# ports:
#   - "4201:80"  # Cambiar 4200 por 4201
```

#### Contenedores no inician
```bash
# Verificar logs detallados
docker-compose logs

# Verificar espacio en disco
df -h

# Verificar memoria disponible
free -h
```

#### Problemas de CORS
- El backend está configurado para permitir CORS desde `http://localhost:4200`
- Si cambias el puerto del frontend, actualiza la configuración CORS en `Backend/app/src/main/java/com/itj/app/config/WebConfig.java`

#### Base de datos H2 no accesible
- Verificar que el backend esté ejecutándose
- La base de datos se reinicia cada vez que se reinicia el contenedor
- Los datos se almacenan en memoria, no son persistentes

### Logs de Debugging

#### Backend
```bash
# Ver logs del backend
docker-compose logs backend

# Ver logs en tiempo real
docker-compose logs -f backend
```

#### Frontend
```bash
# Ver logs del frontend
docker-compose logs frontend

# Ver logs en tiempo real
docker-compose logs -f frontend
```

## 📊 Estructura del Proyecto

```
full-stack-app/
├── Backend/app/                 # Aplicación Spring Boot
│   ├── src/main/java/          # Código fuente Java
│   ├── src/main/resources/     # Configuraciones
│   ├── Dockerfile              # Configuración Docker
│   └── pom.xml                 # Dependencias Maven
├── Frontend/app/               # Aplicación Angular
│   ├── src/app/               # Código fuente TypeScript
│   ├── src/environments/      # Configuraciones de entorno
│   ├── Dockerfile             # Configuración Docker
│   └── package.json           # Dependencias Node.js
├── docker-compose.yml         # Orquestación de servicios
└── README.md                  # Este archivo
```

## 🌐 URLs de Acceso

| Servicio | URL Local | Descripción |
|----------|-----------|-------------|
| Frontend | http://localhost:4200 | Interfaz de usuario Angular |
| Backend API | http://localhost:8080/api | API REST Spring Boot |
| H2 Console | http://localhost:8080/api/h2-console | Consola de base de datos |
| Health Check | http://localhost:8080/api/users/ | Verificación de estado |

## 🔒 Configuración de Seguridad

- **CORS:** Configurado para permitir `http://localhost:4200`
- **Base de datos:** H2 en memoria (solo para desarrollo)
- **Puertos:** 8080 (backend) y 4200 (frontend)

## 📝 Notas Importantes

- ⚠️ **Los datos se pierden** al reiniciar los contenedores (H2 en memoria)
- 🔄 **Reinicio automático** de servicios en caso de fallo
- 📦 **Build automático** de imágenes Docker
- 🌐 **Red interna** `itj-network` para comunicación entre servicios

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 👨‍💻 Autor

**Rom Vargas** - [GitHub](https://github.com/RomVargas)

---

⭐ Si este proyecto te ayudó, ¡dale una estrella!
