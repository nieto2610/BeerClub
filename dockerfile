# Usa una imagen de Maven para compilar la aplicación
FROM maven:3.9.5-jdk-11 AS builder

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo pom.xml y el archivo de definición de dependencias
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Ejecuta el comando para construir la aplicación con Maven Wrapper
RUN ./mvnw clean install -DskipTests

# Crea una imagen de Java para ejecutar la aplicación
FROM openjdk:11-jre-slim

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR generado durante la construcción
COPY --from=builder /app/target/beer_club-back.jar app.jar

# Define el comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
