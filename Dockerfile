FROM maven:3-openjdk-11-slim as build
WORKDIR /app
COPY . /app
#RUN mvn clean compile dependency:go-offline -B
RUN mvn verify clean --fail-never
RUN mvn package


FROM openjdk:11
WORKDIR /app
COPY --from=build /app/target/spring-batch-demo-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "spring-batch-demo-0.0.1-SNAPSHOT.jar"]