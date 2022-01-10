package com.example.einkbitcoinpriceticker.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimeService {

  public static LocalDateTime getCurrentPragueTime() {
    LocalDateTime actualTime = LocalDateTime.now();
    ZoneId zone = ZoneId.of("Europe/Prague");
    ZoneOffset zoneOffSet = zone.getRules().getOffset(actualTime);
    return LocalDateTime.now(zoneOffSet);
  }
}
