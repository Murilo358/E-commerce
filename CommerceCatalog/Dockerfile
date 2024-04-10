FROM jelastic/maven:3.9.5-openjdk-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean install
FROM eclipse-temurin:22-jdk-jammy
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/*.jar ecommerceCatalog.jar
ENTRYPOINT ["java","-jar","/app/ecommerceCatalog.jar"]
