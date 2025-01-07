FROM maven:3.9.6-eclipse-temurin-21-alpine as builder

WORKDIR /app

COPY ./utils-dependencies/pom.xml ./utils-dependencies/pom.xml

RUN mvn -f ./utils-dependencies/pom.xml clean install -DskipTests

COPY ./utils-library/pom.xml ./utils-library/pom.xml
COPY ./utils-library/src ./utils-library/src

RUN mvn -f ./utils-library/pom.xml clean install -DskipTests

COPY ./utils-postgres-schema/pom.xml ./utils-postgres-schema/pom.xml
COPY ./utils-postgres-schema/src ./utils-postgres-schema/src

RUN mvn -f ./utils-postgres-schema/pom.xml clean install -DskipTests

COPY ./notifications_service/pom.xml ./notifications_service/pom.xml
COPY ./notifications_service/src ./notifications_service/src

RUN mvn -f ./notifications_service/pom.xml clean install -DskipTests

FROM eclipse-temurin:21.0.2_13-jre-alpine

WORKDIR /app

COPY --from=builder /app/notifications_service/target/*.jar /app/notifications_service.jar

CMD ["java", "-jar", "notifications_service.jar"]