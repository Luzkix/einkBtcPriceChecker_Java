FROM openjdk:8-jdk-alpine
#required input variables comming from docker run or docker compose.
ENV DB_USERNAME=""
ENV DB_PWD=""
ENV DB_SERVER_IP=""
ENV DB_PORT=""
ENV DB_NAME=""

ENV DB_URL=jdbc:postgresql://$DB_SERVER_IP:$DB_PORT/$DB_NAME

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app/bitcoinpricechecker.jar
EXPOSE 100
ENTRYPOINT ["java","-jar","/app/bitcoinpricechecker.jar"]

