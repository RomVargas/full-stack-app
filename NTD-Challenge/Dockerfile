# Usa una imagen de Maven para construir el proyecto
FROM maven:3.8.4-openjdk-22 AS build
WORKDIR /app

# Copia los archivos de la aplicación y compílala
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Usa una imagen base de OpenJDK
FROM openjdk:22-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el jar de la aplicación al contenedor
COPY --from=build app/target/operations-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre la aplicación
EXPOSE 8080

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]