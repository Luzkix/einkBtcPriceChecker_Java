package com.example.einkbitcoinpriceticker.services;

import com.example.einkbitcoinpriceticker.models.UserIpEntity;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserIpServiceImpl implements UserIpService{
  private Map<String, UserIpEntity>ipAddresses = new HashMap<>();

  @Override
  public void processUserIp(String userIp, String currency, Boolean nightMode, LocalDateTime lastPageRefresh) {
    if(userIp.equals("89.177.93.218")) {userIp = "*Moje IP ";}
    UserIpEntity newIp = new UserIpEntity(userIp,currency,nightMode,lastPageRefresh);
    ipAddresses.put(userIp,newIp);
  }

  @Override
  public List<UserIpEntity> getIpList() {
    List<UserIpEntity> iPlist = ipAddresses.values().stream()
        .sorted(Comparator.comparing(UserIpEntity:: getLastPageRefresh).reversed())
        .collect(Collectors.toList());
    return iPlist;
  }
}
