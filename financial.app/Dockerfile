# Usa una imagen base de JDK con Alpine para un menor tamaño
FROM eclipse-temurin:17-jdk-alpine
# Argumento para el JAR del microservicio (puedes cambiarlo si tu estructura es diferente)
ARG JAR_FILE=target/*.jar
# Copia el archivo JAR generado a la imagen Docker
COPY ${JAR_FILE} app.jar
# Expone el puerto (que es el puerto donde se ejecuta tu aplicación Spring Boot)
EXPOSE 8080
# Comando de inicio del contenedor: ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]