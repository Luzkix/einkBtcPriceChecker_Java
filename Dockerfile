FROM openjdk:8-jdk-alpine
#required input variables comming from docker run or docker compose. Only some of them are used as ENV variables for application.
ENV DB_USERNAME=""
ENV DB_PWD=""
    DB_SERVER_IP=""
    DB_PORT=""
    DB_NAME=""

ENV DB_URL=jdbc:postgresql://$DB_SERVER_IP:$DB_PORT/$DB_NAME

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app/bitcoinpricechecker.jar
EXPOSE 100
ENTRYPOINT ["java","-jar","/app/bitcoinpricechecker.jar"]

