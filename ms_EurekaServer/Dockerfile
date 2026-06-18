# 1. Etapa de compilación (Construye el .jar de forma limpia)
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copiar configuraciones de Maven para descargar dependencias en caché
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copiar código fuente y empaquetar saltando los tests para acelerar el despliegue
COPY src ./src
RUN ./mvnw clean package -DskipTests

# 2. Etapa de ejecución (Crea una caja ligera para correr el servicio)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto nativo de Eureka
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]