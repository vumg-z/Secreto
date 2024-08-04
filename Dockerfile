# Stage 1: Build the application
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Copy Maven files and resolve dependencies
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

# Copy the source code and package the application
COPY src src
RUN ./mvnw package

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

WORKDIR /app

# Install Python, pip, and curl
RUN apt-get update && apt-get install -y python3 python3-pip curl

# Copy the requirements.txt file and install Python dependencies
COPY app/requirements.txt /app/requirements.txt
RUN pip3 install -r /app/requirements.txt

# Copy the built application
COPY --from=build /app/target/*.jar app.jar

# Copy the Python scripts
COPY app/ /app/

# Use a shell script to start the application and run the Python script
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

ENTRYPOINT ["/app/entrypoint.sh"]
