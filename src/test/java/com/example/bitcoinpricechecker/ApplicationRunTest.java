package com.example.bitcoinpricechecker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Test to verify the application can actually run (not just load context).
 */
public class ApplicationRunTest {

    /**
     * Test that the application can start and shutdown successfully.
     * This is a more comprehensive test than just context loading.
     */
    @Test
    public void applicationRunsSuccessfully() {
        // Start the application
        ConfigurableApplicationContext context = SpringApplication.run(
                BitcoinPriceCheckerApplication.class,
                // Disable web server for faster test
                "--server.port=0"
        );

        // Verify the context is active
        assert context.isActive();
        assert context.getParent() == null; // Root context

        // Gracefully shutdown
        SpringApplication.exit(context, () -> 0);

        // Verify it's closed
        assert !context.isActive();
    }
}