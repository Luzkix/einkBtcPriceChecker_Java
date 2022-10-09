package com.example.bitcoinpricechecker.services;

import org.json.JSONObject;

public interface JsonService {
  JSONObject getJson(String currency);
}
