# Use the official OpenJDK image with the latest LTS version
FROM eclipse-temurin:17-jdk-jammy as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build files and download dependencies
COPY pom.xml .
COPY src ./src

# Build the application using Maven (if using Maven)
# If using Gradle, replace with appropriate commands
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests

# Use a smaller runtime image
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/journal-service-*.jar ./journal-service.jar

# Expose the port the app runs on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "journal-service.jar"]