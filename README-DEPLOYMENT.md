# 🚀 Guía de Despliegue

## 📋 Prerrequisitos

- Docker Desktop instalado
- Docker Compose instalado
- Git instalado

## 🐳 Despliegue Local con Docker

### Opción 1: Script Automático
```bash
# Dar permisos de ejecución al script
chmod +x scripts/dev-setup.sh

# Ejecutar script de configuración
./scripts/dev-setup.sh
```

### Opción 2: Pasos Manuales

#### 1. Compilar Backend
```bash
cd Backend/app
./mvnw clean package -DskipTests
cd ../..
```

#### 2. Construir y Ejecutar Contenedores
```bash
docker-compose up --build
```

#### 3. Acceder a la Aplicación
- **Frontend:** http://localhost:4200
- **Backend API:** http://localhost:8080/api
- **H2 Console:** http://localhost:8080/api/h2-console

## ☁️ Despliegue en Render.com

### Backend (Web Service)
1. Crear nuevo **Web Service** en Render
2. Conectar repositorio Git
3. Configuración:
   - **Environment:** Java
   - **Build Command:** `cd Backend/app && ./mvnw clean package -DskipTests`
   - **Start Command:** `cd Backend/app && java -jar target/app-0.0.1-SNAPSHOT.jar`
   - **Port:** 8080

### Frontend (Static Site)
1. Crear nuevo **Static Site** en Render
2. Conectar repositorio Git
3. Configuración:
   - **Build Command:** `cd Frontend/app && npm ci && npm run build`
   - **Publish Directory:** `Frontend/app/dist/app/browser`

### Variables de Entorno
En el Backend, agregar:
- `FRONTEND_URL`: URL del frontend en Render

## 🔧 Comandos Útiles

### Desarrollo Local
```bash
# Ejecutar solo backend
cd Backend/app
./mvnw spring-boot:run

# Ejecutar solo frontend
cd Frontend/app
npm start

# Ejecutar con Docker
docker-compose up --build

# Detener contenedores
docker-compose down

# Ver logs
docker-compose logs -f
```

### Limpieza
```bash
# Limpiar contenedores
docker-compose down -v

# Limpiar imágenes
docker system prune -a

# Limpiar build de backend
cd Backend/app
./mvnw clean
```

## 🐛 Troubleshooting

### Problemas Comunes

#### Backend no inicia
```bash
# Verificar puerto disponible
lsof -i :8080

# Verificar logs
docker-compose logs backend
```

#### Frontend no carga
```bash
# Verificar puerto disponible
lsof -i :4200

# Verificar logs
docker-compose logs frontend
```

#### Problemas de CORS
- Verificar que `FRONTEND_URL` esté configurado correctamente
- Verificar que el frontend use la URL correcta del backend

## 📊 URLs de Desarrollo vs Producción

| Servicio | Desarrollo | Producción |
|----------|------------|------------|
| Frontend | http://localhost:4200 | https://tu-app.onrender.com |
| Backend | http://localhost:8080/api | https://tu-backend.onrender.com/api |
| H2 Console | http://localhost:8080/api/h2-console | https://tu-backend.onrender.com/api/h2-console |
