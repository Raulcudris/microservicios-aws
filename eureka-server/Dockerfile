# Etapa 1: Build (Construcción)
FROM openjdk:11-jdk-slim AS builder

WORKDIR /app

# Copiar Gradle Wrapper y archivos de configuración
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Dar permisos de ejecución al wrapper de Gradle
RUN chmod +x gradlew

# Copiar el código fuente
COPY src src

# Instalar dependencias y compilar la aplicación
RUN ./gradlew clean build -x test

# Etapa 2: Runtime (Ejecución)
FROM gcr.io/distroless/java11-debian11

WORKDIR /app

# Argumento para el nombre del archivo JAR
ARG JAR_FILE=eureka-server-0.0.1-SNAPSHOT.jar

# Copiar el JAR compilado desde la etapa de construcción
COPY --from=builder /app/build/libs/${JAR_FILE} app.jar

# Exponer el puerto correspondiente
EXPOSE 8761

# Ejecutar el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
