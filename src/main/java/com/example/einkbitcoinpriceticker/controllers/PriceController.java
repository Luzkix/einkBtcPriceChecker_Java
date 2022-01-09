package com.example.einkbitcoinpriceticker.controllers;

import com.example.einkbitcoinpriceticker.models.BitcoinPriceEntity;
import com.example.einkbitcoinpriceticker.services.PriceServiceImpl;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class PriceController {

  PriceServiceImpl priceService;

  public PriceController(PriceServiceImpl priceService) {
    this.priceService = priceService;
  }

  @GetMapping({"","/"})
  String displayHomepage(HttpServletRequest request) {
    log.info("Connected IP address: " + request.getRemoteAddr());

    return "homepage";
  }

  @GetMapping("/{nightMode}/{currency}/")
  String displayPrice(@PathVariable String currency, @PathVariable String nightMode, Model model, HttpServletRequest request) {
    log.info("Connected IP address: " + request.getRemoteAddr());

    //if unknown currency, set USD as default
    if(!currency.equals("USD") && !currency.equals("EUR")) {
      currency = "USD";
    }

    model.addAttribute("bitcoinObject", priceService.getPrice(currency));
    model.addAttribute("currency", currency);

    if(nightMode.equals("night")) {
      return "pricePageNight";
    } else return "pricePage";
  }

  @GetMapping("/refresh/{nightMode}/{currency}/")
  String refreshPrice(@PathVariable String currency, @PathVariable String nightMode, Model model) {
    //if unknown currency, set USD as default
    if(!currency.equals("USD") && !currency.equals("EUR")) {
      currency = "USD";
    }

    BitcoinPriceEntity bitcoinPrice = priceService.getPrice(currency);
    long minutes = ChronoUnit.MINUTES.between(bitcoinPrice.getLastUpdate(), LocalDateTime.now());

    if(minutes > 5) {
      LocalDateTime lastUpdate = bitcoinPrice.getLastUpdate();
      bitcoinPrice = new BitcoinPriceEntity();
      bitcoinPrice.setCurrency(currency);
      bitcoinPrice.setPriceChange(0);
      bitcoinPrice.setPriceChangePercentage("0");
      bitcoinPrice.setLastUpdate(lastUpdate);
    }

    model.addAttribute("bitcoinObject", bitcoinPrice);
    model.addAttribute("currency", currency);

    log.info("fragments refreshed on: "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

    if(nightMode.equals("night")) {
      return "pricePageNight :: #priceContainer";
    } else return "pricePage :: #priceContainer";
  }
}
