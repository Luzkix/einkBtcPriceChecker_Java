package com.example.einkbitcoinpriceticker.models;

import com.example.einkbitcoinpriceticker.services.TimeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BitcoinPriceDTO {

  private int actualPrice;
  private int priceChange;
  private String priceChangePercentage;
  private String priceChangeSign;
  private String currency;
  private LocalDateTime lastUpdate;

  public BitcoinPriceDTO(int actualPrice, int priceChange,
                         String priceChangePercentage, String priceChangeSign,
                         String currency) {
    this.actualPrice = actualPrice;
    this.priceChange = priceChange;
    this.priceChangePercentage = priceChangePercentage;
    this.priceChangeSign = priceChangeSign;
    this.currency = currency;
    this.lastUpdate = TimeService.getCurrentPragueTime();
  }
}
