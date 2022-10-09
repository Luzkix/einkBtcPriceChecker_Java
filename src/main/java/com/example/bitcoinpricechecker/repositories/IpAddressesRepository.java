package com.example.bitcoinpricechecker.repositories;

import com.example.bitcoinpricechecker.models.IpAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IpAddressesRepository extends JpaRepository<IpAddressEntity, Long> {
    @Query(value = "SELECT * FROM btc.ip_addresses WHERE ip_address = :ipAddress LIMIT 1", nativeQuery = true)
    IpAddressEntity findByIpAddress(String ipAddress);

    @Query(value = "SELECT * FROM btc.ip_addresses WHERE last_page_refresh >= current_date - 1 ORDER BY last_page_refresh DESC", nativeQuery = true)
    List<IpAddressEntity> findAllActiveIpAddresses();
}
