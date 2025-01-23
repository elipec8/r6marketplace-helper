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

COPY ./utils-postgres-schema/pom.xml ./utils-postgres-schema/pom.xml
COPY ./utils-postgres-schema/src ./utils-postgres-schema/src

RUN mvn -f ./utils-postgres-schema/pom.xml clean install -DskipTests

COPY ./utils-library-graphQl/pom.xml ./utils-library-graphQl/pom.xml
COPY ./utils-library-graphQl/src ./utils-library-graphQl/src

RUN mvn -f ./utils-library-graphQl/pom.xml clean install -DskipTests

COPY ./utils-library-kafka/pom.xml ./utils-library-kafka/pom.xml
COPY ./utils-library-kafka/src ./utils-library-kafka/src

RUN mvn -f ./utils-library-kafka/pom.xml clean install -DskipTests

COPY ./item_stats_fetcher/pom.xml ./item_stats_fetcher/pom.xml
COPY ./item_stats_fetcher/src ./item_stats_fetcher/src

RUN mvn -f ./item_stats_fetcher/pom.xml clean install -DskipTests

FROM eclipse-temurin:21.0.2_13-jre-alpine

WORKDIR /app

COPY --from=builder /app/item_stats_fetcher/target/*.jar /app/item_stats_fetcher.jar

CMD ["java", "-jar", "item_stats_fetcher.jar"]