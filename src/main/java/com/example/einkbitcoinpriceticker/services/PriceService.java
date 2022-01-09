package com.example.einkbitcoinpriceticker.services;

import com.example.einkbitcoinpriceticker.models.BitcoinPriceDTO;

public interface PriceService {
  BitcoinPriceDTO getPrice(String currency);
}
