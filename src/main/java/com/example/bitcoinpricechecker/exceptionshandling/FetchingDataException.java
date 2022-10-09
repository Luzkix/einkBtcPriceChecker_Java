package com.example.bitcoinpricechecker.exceptionshandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom response exception for handling errors related to fetching data from DB.
 * @author Zdenek Luzar
 */
@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
public class FetchingDataException extends Exception {
    public FetchingDataException(String errorMessage) {
        super(errorMessage);
    }
}