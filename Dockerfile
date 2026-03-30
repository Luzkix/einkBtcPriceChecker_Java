FROM eclipse-temurin:21-jre-alpine

#required input variables comming from docker run or docker compose.
ENV DB_USERNAME=""
ENV DB_PWD=""
ENV DB_URL=""

ARG JAR_FILE=build/libs/bitcoinPriceChecker-latest.jar
COPY ${JAR_FILE} app/bitcoinpricechecker.jar
EXPOSE 100
ENTRYPOINT ["java","-jar","/app/bitcoinpricechecker.jar"]

