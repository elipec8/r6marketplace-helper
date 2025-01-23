FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY ./utils-dependencies/pom.xml ./utils-dependencies/pom.xml

RUN mvn -f ./utils-dependencies/pom.xml clean install -DskipTests

COPY ./utils-library/pom.xml ./utils-library/pom.xml
COPY ./utils-library/src ./utils-library/src

RUN mvn -f ./utils-library/pom.xml clean install -DskipTests

COPY ./utils-library-redis/pom.xml ./utils-library-redis/pom.xml
COPY ./utils-library-redis/src ./utils-library-redis/src

RUN mvn -f ./utils-library-redis/pom.xml clean install -DskipTests

COPY ./main_user_reauthorizer/pom.xml ./main_user_reauthorizer/pom.xml
COPY ./main_user_reauthorizer/src ./main_user_reauthorizer/src

RUN mvn -f ./main_user_reauthorizer/pom.xml clean install -DskipTests

FROM eclipse-temurin:21.0.2_13-jre-alpine

WORKDIR /app

COPY --from=builder /app/main_user_reauthorizer/target/*.jar /app/main_user_reauthorizer.jar

CMD ["java", "-jar", "main_user_reauthorizer.jar"]