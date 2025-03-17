FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

COPY build/libs/*.jar app.jar

RUN chown -R spring:spring /app

USER spring

EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=dev
ENV SERVER_PORT=8080

ENTRYPOINT ["java", "-jar", "app.jar"]