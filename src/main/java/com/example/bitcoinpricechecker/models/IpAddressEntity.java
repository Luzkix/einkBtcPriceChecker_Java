package com.example.bitcoinpricechecker.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "ip_addresses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IpAddressEntity {
  public IpAddressEntity(String ipAddress, String currency, Boolean nightMode, LocalDateTime lastPageRefresh){
    this.ipAddress = ipAddress;
    this.currency = currency;
    this.nightMode = nightMode;
    this.lastPageRefresh = lastPageRefresh;
  }
  public IpAddressEntity(String ipAddress, String owner, String currency, Boolean nightMode, LocalDateTime lastPageRefresh){
    this.ipAddress = ipAddress;
    this.owner = owner;
    this.currency = currency;
    this.nightMode = nightMode;
    this.lastPageRefresh = lastPageRefresh;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  String ipAddress;

  String owner = null;
  @NotNull
  String currency;
  @NotNull
  Boolean nightMode;
  @NotNull
  LocalDateTime lastPageRefresh;

}
