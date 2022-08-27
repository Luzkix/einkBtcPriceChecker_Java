package com.example.einkbitcoinpriceticker.services;

import com.example.einkbitcoinpriceticker.models.BitcoinPriceDTO;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

@Slf4j
@Service
public class PriceServiceImpl implements PriceService {
  private JsonService jsonService;

  public PriceServiceImpl(JsonService jsonService) {
    this.jsonService = jsonService;
  }

  private final int UPDATEINTERVAL = 10;
  private BitcoinPriceDTO usdOldPrice = new BitcoinPriceDTO(0,0,"0,00","+","$");
  private BitcoinPriceDTO eurOldPrice = new BitcoinPriceDTO(0,0,"0,00","+","€");

  public BitcoinPriceDTO getPrice(String currency) {
    //if UPDATEINTERVAL was not exceeded yet, previous prices are returned
    if (currency.equals("USD") && calculateSecondsFromLastUpdate(currency) < UPDATEINTERVAL
        && usdOldPrice.getActualPrice() != 0) return usdOldPrice;
    if (currency.equals("EUR") && calculateSecondsFromLastUpdate(currency) < UPDATEINTERVAL
        && eurOldPrice.getActualPrice() != 0) return eurOldPrice;

    //get new json from Coinbase API
    JSONObject json = jsonService.getJson(currency);

    //Convert json to the bitcoin price model
    BitcoinPriceDTO bitcoinNewPrice = convertJsonToBitcoinPriceEntity(json, currency);
    if (bitcoinNewPrice == null) {
      log.error("New bitcoin price could not be retrieved from Coinbase - old value is displayed instead");
      if (currency.equals("USD")) {
        return usdOldPrice;
      } else return eurOldPrice;
    } else {
      if (currency.equals("USD")) {
        usdOldPrice = bitcoinNewPrice;
        return bitcoinNewPrice;
      } else {
        eurOldPrice = bitcoinNewPrice;
        return bitcoinNewPrice;
      }
    }
  }

  private long calculateSecondsFromLastUpdate(String currency) {
    if (currency.equals("USD")) {
      return ChronoUnit.SECONDS.between(usdOldPrice.getLastUpdate(), TimeService.getCurrentPragueTime());
    } else return ChronoUnit.SECONDS.between(eurOldPrice.getLastUpdate(), TimeService.getCurrentPragueTime());
  }

  public BitcoinPriceDTO convertJsonToBitcoinPriceEntity(JSONObject json, String currency) {
    try {
      String open = json.getString("open");
      String last = json.getString("last");

      int yesterdayPrice = (int)(Double.parseDouble(open));
      int actualPrice = (int)(Double.parseDouble(last));
      int priceChangeUSD = actualPrice - yesterdayPrice;
      String priceChangeSign = "+";

      float priceChangePercentageFloat = (((float)actualPrice/(float)yesterdayPrice) - 1)*100;
      if(priceChangePercentageFloat < 0) {
        priceChangeSign = "-";
        priceChangePercentageFloat = Math.abs(priceChangePercentageFloat);
      }

      String priceChangePercentage = String.format("%.2f", priceChangePercentageFloat);

      if (currency.equals("USD")) {
        currency = "$";
      } else if (currency.equals("EUR")) {
        currency = "€";
      } else currency = "$";

      LocalDateTime lastUpdate = TimeService.getCurrentPragueTime();

      return new BitcoinPriceDTO(actualPrice,priceChangeUSD,priceChangePercentage,
          priceChangeSign,currency,lastUpdate);
    }
    catch (Exception e) {
      log.error("Price json from Coinbase was not successfully converted into BitcoinPriceDTO");
      return null;
    }
  }

  public BitcoinPriceDTO getRefreshedPrice(String currency) {
    BitcoinPriceDTO bitcoinPrice = getPrice(currency);
    long minutes = ChronoUnit.MINUTES.between(bitcoinPrice.getLastUpdate(), LocalDateTime.now());

    if(minutes > 5) {
      LocalDateTime lastUpdate = bitcoinPrice.getLastUpdate();
      bitcoinPrice = new BitcoinPriceDTO();
      bitcoinPrice.setCurrency(currency);
      bitcoinPrice.setPriceChange(0);
      bitcoinPrice.setPriceChangePercentage("0");
      bitcoinPrice.setLastUpdate(lastUpdate);
    }

    return bitcoinPrice;
  }
}
