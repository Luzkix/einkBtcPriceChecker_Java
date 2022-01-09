package com.example.einkbitcoinpriceticker.services;

import com.example.einkbitcoinpriceticker.models.UserIpEntity;
import java.time.LocalDateTime;
import java.util.List;

public interface UserIpService {
  void processUserIp(String userIp, String currency , Boolean nightMode, LocalDateTime lastPageRefresh);
  List<UserIpEntity> getIpList();
}
