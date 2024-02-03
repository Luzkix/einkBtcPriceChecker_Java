# E-ink Bitcoin Price Checker

This miniproject is about building easy to use application/webpage which shows actual price of Bitcoin in USD/EUR. The view is very simplistic, e-ink optimised (I build it primarily for my android based e-ink book reader Likebook Mars). Data are gathered from free to use API from Coinbase.

The idea was to build 'Bitcoin Price Checker' using various technologies even if the technology itself might be not most suitable for this kind of simple app (e.g. overkill). However, ultimate goal was to practice and learn these technologies.


## APP versions

### Java Spring Boot version
- Probably overkill, the goal was to practice Java and Spring Boot + Thymeleaf templates. Result is a Server-Side rendered web page, which is being auto-refreshed every x seconds. The project is hosted on my home NAS server.

### DB SETUP
- create Postgres DB (e.g. named "eink_btc_price_checker") with DB schema "btc"
- create your own user with password with sufficient privileges to make DB changes
- import the project into your IDE
- run flyway migrations to create necessary DB tables, e.g. by creating flyway configuration in your IDE with following environmental variables:
  - FLYWAY_USER={yourDbUsername};
  - FLYWAY_PASSWORD={yourDbPassword};
  - FLYWAY_URL=jdbc:postgresql://{dbServerIp}:{dbPort}/{dbName};
  - FLYWAY_SCHEMAS=btc;
- now the DB shoud be ready for connecting to the application

### Running Docker image

- Latest docker image of the app can be found here: https://hub.docker.com/r/luzkix/bitcoinpricechecker
- Docker container internally uses port 100, which can't be changed by passing ENV variable. However, you can map different local port to this container using e.g. docker run command -p 80:100
- Environmental variables - you need to pass following ENV variables to connect the application with your database:
  - DB_USERNAME={yourDbUsername}
  - DB_PWD={yourDbPassword}
  - DB_SERVER_IP={yourDbServerIp}
  - DB_PORT={yourDbPort}
  - DB_NAME={databaseName}
  - Final docker run command should look like this (just example):
    - sudo docker run \
        -d \
        --name bitcoinpricechecker \
        -p 100:5800 \
        -e DB_USERNAME="name" \
        -e DB_PWD="password" \
        -e DB_SERVER_IP="192.168.1.105" \
        -e DB_PORT=5432 \
        -e DB_NAME="eink_btc_price_checker" \
        --network=host \
        --restart always \
        luzkix/bitcoinpricechecker:latest