FROM jelastic/maven:3.9.5-openjdk-21 AS build
WORKDIR /app
COPY ./pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn clean package

FROM eclipse-temurin:21-jdk-jammy
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/*.jar ecommerceServiceDiscovery.jar
ENTRYPOINT ["java","-jar","/app/ecommerceServiceDiscovery.jar"]