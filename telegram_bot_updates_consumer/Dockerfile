FROM maven:3.9.6-eclipse-temurin-21-alpine as builder

WORKDIR /app

COPY ./utils-dependencies/pom.xml ./utils-dependencies/pom.xml

RUN mvn -f ./utils-dependencies/pom.xml clean install -DskipTests

COPY ./utils-library/pom.xml ./utils-library/pom.xml
COPY ./utils-library/src ./utils-library/src

RUN mvn -f ./utils-library/pom.xml clean install -DskipTests

COPY ./utils-library-redis/pom.xml ./utils-library-redis/pom.xml
COPY ./utils-library-redis/src ./utils-library-redis/src

RUN mvn -f ./utils-library-redis/pom.xml clean install -DskipTests

COPY ./utils-postgres-schema/pom.xml ./utils-postgres-schema/pom.xml
COPY ./utils-postgres-schema/src ./utils-postgres-schema/src

RUN mvn -f ./utils-postgres-schema/pom.xml clean install -DskipTests

COPY ./telegram_bot_updates_consumer/pom.xml ./telegram_bot_updates_consumer/pom.xml
COPY ./telegram_bot_updates_consumer/src ./telegram_bot_updates_consumer/src

RUN mvn -f ./telegram_bot_updates_consumer/pom.xml clean install -DskipTests

FROM eclipse-temurin:21.0.2_13-jre-alpine

WORKDIR /app

COPY --from=builder /app/telegram_bot_updates_consumer/target/*.jar /app/telegram_bot_updates_consumer.jar

CMD ["java", "-jar", "telegram_bot_updates_consumer.jar"]