package com.example.bitcoinpricechecker.services;

import com.example.bitcoinpricechecker.exceptionshandling.CoinbaseApiException;
import org.json.JSONObject;

import java.io.IOException;

public interface JsonService {
  JSONObject getJson(String currency) throws CoinbaseApiException, IOException;
}
