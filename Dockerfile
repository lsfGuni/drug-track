# Use a base image with JDK
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the build jar file to the container
COPY build/libs/your-app-name-0.0.1-SNAPSHOT.jar app.jar

# Specify the command to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
