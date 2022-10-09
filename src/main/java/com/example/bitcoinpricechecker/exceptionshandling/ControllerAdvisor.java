package com.example.bitcoinpricechecker.exceptionshandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

  @ExceptionHandler({
      FetchingDataException.class
  })
  public ResponseEntity<ErrorDto> handle503Exceptions(Exception ex) {
    HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;

    log.error(ex.getMessage());

    return new ResponseEntity<>(new ErrorDto(status.name(), status.value(), ex.getMessage()), status);
  }




}
