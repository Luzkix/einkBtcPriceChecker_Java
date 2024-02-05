package com.example.bitcoinpricechecker.services;

import com.example.bitcoinpricechecker.exceptionshandling.FetchingDataException;
import com.example.bitcoinpricechecker.models.IpAddressEntity;
import com.example.bitcoinpricechecker.repositories.IpAddressesRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
public class IpAddressesServiceTest {
    private IpAddressesRepository ipAddressesRepository;
    private IpAddressesServiceImpl ipService;

    @Before
    public void setUp() {
        ipAddressesRepository = Mockito.mock(IpAddressesRepository.class);
        ipService = Mockito.spy(new IpAddressesServiceImpl(ipAddressesRepository));
    }

    @Test
    public void testProcessIpAddress_validInput_returnsIpAddressEntity() throws FetchingDataException {
        String ipAddress = "127.0.0.1";
        String currency = "USD";
        Boolean nightMode = false;
        LocalDateTime lastRefresh = LocalDateTime.now();

        IpAddressEntity returnedFromDb = new IpAddressEntity(ipAddress,"EUR",true, lastRefresh);
        IpAddressEntity expected = new IpAddressEntity(ipAddress, currency, nightMode, lastRefresh);

        Mockito.doReturn(returnedFromDb).when(ipAddressesRepository).findByIpAddress(ipAddress);
        Mockito.doReturn(returnedFromDb).when(ipAddressesRepository).save(any());

        IpAddressEntity result = ipService.processIpAddress(ipAddress, currency, nightMode, lastRefresh);

        assertEquals(expected.getIpAddress(), result.getIpAddress());
        assertEquals(expected.getCurrency(), result.getCurrency());
        assertEquals(expected.getNightMode(), result.getNightMode());
        assertEquals(expected.getLastPageRefresh(), result.getLastPageRefresh());
        verify(ipService).processIpAddress(ipAddress, currency, nightMode, lastRefresh);
    }

    @Test(expected = FetchingDataException.class)
    public void testProcessIpAddress_throwsException() throws FetchingDataException {
        String ipAddress = "127.0.0.1";

        when(ipAddressesRepository.findByIpAddress(ipAddress))
                .thenThrow(new InvalidDataAccessResourceUsageException("Invalid IP"));

        ipService.processIpAddress(ipAddress, "USD", false, LocalDateTime.now());
    }
}
