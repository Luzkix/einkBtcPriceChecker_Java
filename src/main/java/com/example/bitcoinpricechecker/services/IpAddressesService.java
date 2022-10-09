package com.example.bitcoinpricechecker.services;

import com.example.bitcoinpricechecker.exceptionshandling.FetchingDataException;
import com.example.bitcoinpricechecker.models.IpAddressEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface IpAddressesService {
  IpAddressEntity processIpAddress(String ipAddress, String currency , Boolean nightMode, LocalDateTime lastPageRefresh) throws FetchingDataException;
  List<IpAddressEntity> geAllActiveIpAddresses() throws FetchingDataException;
  List<IpAddressEntity> geAllIpAddresses() throws FetchingDataException;
}
