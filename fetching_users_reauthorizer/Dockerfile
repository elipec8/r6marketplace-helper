FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY ./utils-dependencies/pom.xml ./utils-dependencies/pom.xml

RUN mvn -f ./utils-dependencies/pom.xml clean install -DskipTests

COPY ./utils-library/pom.xml ./utils-library/pom.xml
COPY ./utils-library/src ./utils-library/src

RUN mvn -f ./utils-library/pom.xml clean install -DskipTests

COPY ./utils-postgres-schema/pom.xml ./utils-postgres-schema/pom.xml
COPY ./utils-postgres-schema/src ./utils-postgres-schema/src

RUN mvn -f ./utils-postgres-schema/pom.xml clean install -DskipTests

COPY ./fetching_users_reauthorizer/pom.xml ./fetching_users_reauthorizer/pom.xml
COPY ./fetching_users_reauthorizer/src ./fetching_users_reauthorizer/src

RUN mvn -f ./fetching_users_reauthorizer/pom.xml clean install -DskipTests

FROM eclipse-temurin:21.0.2_13-jre-alpine

WORKDIR /app

COPY --from=builder /app/fetching_users_reauthorizer/target/*.jar /app/fetching_users_reauthorizer.jar

CMD ["java", "-jar", "fetching_users_reauthorizer.jar"]