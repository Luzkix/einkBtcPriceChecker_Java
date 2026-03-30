package com.example.bitcoinpricechecker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Basic test to verify the application can start up without errors.
 */
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationStartupTest {

    /**
     * Test that the Spring application context loads successfully.
     */
    @Test
    public void contextLoads() {
        // This test passes if the application context loads without throwing an exception
    }

    /**
     * Test that the main application class can be instantiated.
     */
    @Test
    public void mainApplicationClassCanBeInstantiated() {
        // This test verifies that the main class exists and can be instantiated
        BitcoinPriceCheckerApplication app = new BitcoinPriceCheckerApplication();
        assert app != null;
    }
}