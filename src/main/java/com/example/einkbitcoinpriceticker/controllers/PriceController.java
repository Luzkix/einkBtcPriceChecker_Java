package com.example.einkbitcoinpriceticker.controllers;

import com.example.einkbitcoinpriceticker.models.BitcoinPriceDTO;
import com.example.einkbitcoinpriceticker.models.UserIpEntity;
import com.example.einkbitcoinpriceticker.services.PriceServiceImpl;
import com.example.einkbitcoinpriceticker.services.TimeService;
import com.example.einkbitcoinpriceticker.services.UserIpService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
  UserIpService userIpService;

  public PriceController(PriceServiceImpl priceService, UserIpService userIpService) {
    this.priceService = priceService;
    this.userIpService = userIpService;
  }

  @GetMapping({"","/"})
  String displayHomepage(HttpServletRequest request) {
    log.info("Connected IP address: " + request.getRemoteAddr());

    return "homepage";
  }

  @GetMapping("/{nightMode}/{currency}/")
  String displayPrice(@PathVariable String currency, @PathVariable String nightMode, Model model,
                      HttpServletRequest request) {
    log.info("Connected IP address: " + request.getRemoteAddr());

    //if unknown currency, set USD as default
    if(!currency.equals("USD") && !currency.equals("EUR")) {
      currency = "USD";
    }

    userIpService.processUserIp(request.getRemoteAddr(),currency,(nightMode.equals("night")), TimeService.getCurrentPragueTime());

    model.addAttribute("bitcoinObject", priceService.getPrice(currency));
    model.addAttribute("currency", currency);

    if(nightMode.equals("night")) {
      return "pricePageNight";
    } else return "pricePage";
  }

  @GetMapping("/refresh/{nightMode}/{currency}/")
  String refreshPrice(@PathVariable String currency, @PathVariable String nightMode, Model model) {
    //ipList is not used anyhow, it is just to keep the map alive
    List<UserIpEntity> iPlist = userIpService.getIpList();

    //if unknown currency, set USD as default
    if(!currency.equals("USD") && !currency.equals("EUR")) {
      currency = "USD";
    }

    BitcoinPriceDTO bitcoinPrice = priceService.getPrice(currency);
    long minutes = ChronoUnit.MINUTES.between(bitcoinPrice.getLastUpdate(), LocalDateTime.now());

    if(minutes > 5) {
      LocalDateTime lastUpdate = bitcoinPrice.getLastUpdate();
      bitcoinPrice = new BitcoinPriceDTO();
      bitcoinPrice.setCurrency(currency);
      bitcoinPrice.setPriceChange(0);
      bitcoinPrice.setPriceChangePercentage("0");
      bitcoinPrice.setLastUpdate(lastUpdate);
    }

    model.addAttribute("bitcoinObject", bitcoinPrice);
    model.addAttribute("currency", currency);

    log.info("fragments refreshed on: "+ TimeService.getCurrentPragueTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

    if(nightMode.equals("night")) {
      return "pricePageNight :: #priceContainer";
    } else return "pricePage :: #priceContainer";
  }

  @GetMapping("/stats")
  String showStats(Model model) {
    List<UserIpEntity> iPlist = userIpService.getIpList();
    model.addAttribute("iPlist", iPlist);
    model.addAttribute("totalIps",iPlist.size());

    return "statsPage";
  }
}
