# Usa una imagen base de JDK con Alpine
FROM eclipse-temurin:17-jdk-alpine

# Argumento para el JAR del microservicio
ARG JAR_FILE=target/*.jar

# Copia el archivo JAR generado al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto
EXPOSE 8080

# Comando de inicio del contenedor
ENTRYPOINT ["java", "-jar", "/app.jar"]