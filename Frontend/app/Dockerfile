# Etapa de build
FROM node:20-alpine AS build

WORKDIR /app

# Copiar package.json y package-lock.json
COPY package*.json ./

# Instalar dependencias
RUN npm ci

# Copiar código fuente
COPY . .

# Build de la aplicación
RUN npm run build

# Etapa de producción con nginx
FROM nginx:alpine

# Instalar envsubst para procesar variables de entorno
RUN apk add --no-cache bash

# Copiar archivos buildados
COPY --from=build /app/dist/app/browser /usr/share/nginx/html

# Copiar configuración de nginx
COPY nginx.conf /etc/nginx/nginx.conf.template

# Crear script de inicio
RUN echo '#!/bin/bash\n\
envsubst < /etc/nginx/nginx.conf.template > /etc/nginx/nginx.conf\n\
nginx -g "daemon off;"' > /docker-entrypoint.sh && \
chmod +x /docker-entrypoint.sh

# Exponer puerto
EXPOSE 80

# Comando por defecto
CMD ["/docker-entrypoint.sh"]
