FROM jelastic/maven:3.9.5-openjdk-21 AS build
WORKDIR /app
COPY .. .
RUN mvn clean install
FROM eclipse-temurin:21-jdk-jammy
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/*.jar ecommerceGateway.jar
ENTRYPOINT ["java","-jar","/app/ecommerceGateway.jar"]
