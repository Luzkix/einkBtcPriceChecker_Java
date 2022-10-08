package com.example.einkbitcoinpriceticker.services;

import com.example.einkbitcoinpriceticker.exceptionshandling.FetchingDataException;
import com.example.einkbitcoinpriceticker.models.IpAddressEntity;
import com.example.einkbitcoinpriceticker.repositories.IpAddressesRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class IpAddressesServiceImpl implements IpAddressesService {

  private IpAddressesRepository ipAddressesRepository;

  @Override
  public IpAddressEntity processIpAddress(String ipAddress, String currency, Boolean nightMode, LocalDateTime lastPageRefresh)
          throws FetchingDataException {

    if(ipAddress.startsWith("127.0.") || ipAddress.startsWith("0") || ipAddress.startsWith("192.168.")
            || ipAddress.equals("192.168.1.1")) {
      ipAddress = "*Zdendovy domácí IP ";
    }

    IpAddressEntity newIpAddress = new IpAddressEntity(ipAddress,currency,nightMode,lastPageRefresh);
    IpAddressEntity ipAddressFromDb;

    try {
      ipAddressFromDb = ipAddressesRepository.findByIpAddress(ipAddress);
    } catch (InvalidDataAccessResourceUsageException ex) {
      throw new FetchingDataException(ex.getMessage());
    }

    if(ipAddressFromDb == null) {
      return ipAddressesRepository.save(newIpAddress);
    } else {
      ipAddressFromDb.setCurrency(currency);
      ipAddressFromDb.setNightMode(nightMode);
      ipAddressFromDb.setLastPageRefresh(lastPageRefresh);
      return ipAddressesRepository.save(ipAddressFromDb);
    }
  }

  @Override
  public List<IpAddressEntity> geAllActiveIpAddresses() throws FetchingDataException {
    try {
      List<IpAddressEntity> ipAddresses = ipAddressesRepository.findAllActiveIpAddresses();
      return ipAddresses;
    } catch (InvalidDataAccessResourceUsageException ex) {
      throw new FetchingDataException(ex.getMessage());
    }
  }

  @Override
  public List<IpAddressEntity> geAllIpAddresses() throws FetchingDataException {
    try {
      return ipAddressesRepository.findAll();
    } catch (InvalidDataAccessResourceUsageException ex) {
      throw new FetchingDataException(ex.getMessage());
    }
  }
}
