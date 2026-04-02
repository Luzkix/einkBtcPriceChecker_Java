FROM eclipse-temurin:21-jre-alpine

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app/bitcoinpricechecker.jar
EXPOSE 100
ENTRYPOINT ["java","-jar","/app/bitcoinpricechecker.jar"]

