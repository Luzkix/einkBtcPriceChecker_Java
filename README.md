# E-ink Bitcoin Price Checker

A simple web application that displays the current Bitcoin price in USD or EUR. Data is fetched from the Coinbase API and refreshed every 30 seconds.

Built primarily for e-ink readers and mobile devices, but works in any browser.

## Tech Stack

- Java 21 + Spring Boot
- Thymeleaf templates (server-side rendering)
- Coinbase public API

## Running Locally

```bash
./gradlew bootRun
```

The app starts on port 100: http://localhost:100

## Running with Docker

Latest Docker image: https://hub.docker.com/r/luzkix/bitcoinpricechecker

```bash
docker run -d --name bitcoinpricechecker -p 100:100 luzkix/bitcoinpricechecker:latest
```

The container listens on port 100 internally. Map a different local port with `-p 80:100` if needed.

## Endpoints

| Path | Description |
|------|-------------|
| `/` | Homepage - currency selection |
| `/{day\|night}/{USD\|EUR}/` | BTC price page |
| `/refresh/{USD\|EUR}/` | Price fragment (auto-refresh) |

## Source

https://github.com/Luzkix/einkBtcPriceChecker_Java
