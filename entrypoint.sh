#!/bin/bash

# Start the Spring Boot application in the background
echo "Starting Spring Boot application..."
java -jar app.jar &

# Function to check if the application is ready
check_app_ready() {
  # Check if the application is responding
  curl --silent --fail localhost:8081/actuator/health > /dev/null
}

# Wait for the application to be ready
echo "Waiting for the application to be ready..."
until check_app_ready; do
  echo "Application not ready yet. Retrying in 5 seconds..."
  sleep 5
done

echo "Application is ready."

# Run the Python command
echo "Running the Python script..."
python3 /app/start.py

# Wait for all background processes to complete
wait
