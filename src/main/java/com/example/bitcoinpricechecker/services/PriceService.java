package com.example.bitcoinpricechecker.services;

import com.example.bitcoinpricechecker.models.BitcoinPriceDTO;

public interface PriceService {
  BitcoinPriceDTO getPrice(String currency);
  BitcoinPriceDTO getRefreshedPrice(String currency);
}
