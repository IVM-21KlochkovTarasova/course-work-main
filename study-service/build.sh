./mvnw clean package
docker build -f src/main/docker/Dockerfile.jvm -t study-service .
