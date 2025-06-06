# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Create a directory inside the container for the app
WORKDIR /app

# Copy built JAR from target folder into the container
COPY target/TrackingNumberService-0.0.1-SNAPSHOT.jar app.jar

# Expose port (same as Spring Boot default)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]

