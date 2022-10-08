FROM openjdk:8-jdk-alpine

ENV DB_PWD=test
ENV DB_URL=jdbc:postgresql://192.168.1.10:5433/eink_btc_price_checker
ENV DB_USERNAME=luzar

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app/bitcoinpricechecker.jar
EXPOSE 100
ENTRYPOINT ["java","-jar","/app/bitcoinpricechecker.jar"]

