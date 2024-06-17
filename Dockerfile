FROM openjdk:17-jdk-alpine
COPY build/libs/SpringApplication-0.0.1-SNAPSHOT.jar SpringApplication.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/SpringApplication.jar"]
