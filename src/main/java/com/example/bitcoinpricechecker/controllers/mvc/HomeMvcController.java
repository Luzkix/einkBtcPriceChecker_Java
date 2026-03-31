package com.example.bitcoinpricechecker.controllers.mvc;

import com.example.bitcoinpricechecker.models.BitcoinPriceDTO;
import com.example.bitcoinpricechecker.services.PriceService;
import com.example.bitcoinpricechecker.services.TimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;

@Slf4j
@AllArgsConstructor
@Controller
public class HomeMvcController {

  private PriceService priceService;

  @GetMapping({"","/"})
  String displayHomepage(HttpServletRequest request) {
    System.out.println("Connected IP address: " + request.getRemoteAddr());

    return "homepage";
  }

  @GetMapping("/{nightMode}/{currency}/")
  String displayPrice(@PathVariable String currency, @PathVariable String nightMode, Model model,
                      HttpServletRequest request) {
    //if unknown currency, set USD as default
    currency = currency.equals("USD") || currency.equals("EUR") ? currency : "USD";

    log.info("Connected IP address: " + request.getRemoteAddr());

    model.addAttribute("bitcoinObject", priceService.getPrice(currency));
    model.addAttribute("currency", currency);
    model.addAttribute("nightMode", nightMode.equals("night"));

    return "pricePage";
  }

  @GetMapping("/refresh/{currency}/")
  String refreshPrice(@PathVariable String currency, Model model) {
    //if unknown currency, set USD as default
    currency = currency.equals("USD") || currency.equals("EUR") ? currency : "USD";

    BitcoinPriceDTO bitcoinPrice = priceService.getRefreshedPrice(currency);

    model.addAttribute("bitcoinObject", bitcoinPrice);
    model.addAttribute("currency", currency);

    System.out.println("fragments refreshed on: "+ TimeService.getCurrentPragueTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

    return "pricePage :: #priceContainer";
  }
}
