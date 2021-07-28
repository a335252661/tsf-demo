FROM openjdk:8-jre
WORKDIR /app
ADD *.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar"]
CMD ["app.jar"]