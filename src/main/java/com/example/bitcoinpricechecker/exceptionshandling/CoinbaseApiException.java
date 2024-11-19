package com.example.bitcoinpricechecker.exceptionshandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom response exception for handling errors related to fetching data from DB.
 * @author Zdenek Luzar
 */
@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
public class CoinbaseApiException extends Exception {
    public CoinbaseApiException(String errorMessage) {
        super(errorMessage);
    }
}