FROM openjdk:17-alpine
EXPOSE 9083
ADD /target/api-gateway.jar api-gateway.jar
ENTRYPOINT ["java","-jar","/api-gateway.jar"]