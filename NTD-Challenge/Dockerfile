# Usa una imagen de Maven para construir el proyecto
# Usa OpenJDK 22 como base
FROM openjdk:22-jdk-slim AS build

# Instala Maven
ARG MAVEN_VERSION=3.9.8
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://dlcdn.apache.org/maven/maven-3/3.9.8/binaries/apache-maven-3.9.8-bin.tar.gz && \
    tar -xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven

# Configura el entorno
ENV MAVEN_HOME /opt/maven
ENV PATH ${MAVEN_HOME}/bin:${PATH}
WORKDIR /app

# Copia los archivos de la aplicación y compílala
COPY pom.xml .
COPY src ./src
RUN mvn clean install

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