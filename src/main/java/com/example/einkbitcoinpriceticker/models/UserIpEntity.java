package com.example.einkbitcoinpriceticker.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserIpEntity {
  String userIp;
  String currency;
  Boolean nightMode;
  LocalDateTime lastPageRefresh;
}
