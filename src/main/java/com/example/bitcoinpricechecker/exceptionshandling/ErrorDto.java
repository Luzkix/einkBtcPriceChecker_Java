package com.example.bitcoinpricechecker.exceptionshandling;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDto {
  private Boolean error = true;
  private String status;
  private Integer statusCode;
  private String message;

  public ErrorDto(String status, Integer statusCode, String message) {
    this.status = status;
    this.statusCode = statusCode;
    this.message = message;
  }
}
