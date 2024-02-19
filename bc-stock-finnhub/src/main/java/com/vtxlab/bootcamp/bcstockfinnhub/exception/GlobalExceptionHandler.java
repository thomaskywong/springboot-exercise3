package com.vtxlab.bootcamp.bcstockfinnhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.ApiResponse;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidStockSymbolException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResponse<Void> InvalidStockSymbolExceptionHandler(
      InvalidStockSymbolException ex) {
    return ApiResponse.<Void>builder() //
        .code(Syscode.INVALID_STOCK_SYMBOL.getCode()) //
        .message(Syscode.INVALID_STOCK_SYMBOL.getMessage()) //
        .data(null) //
        .build();
  }

  // @ExceptionHandler(InvalidCurrencyException.class)
  // @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  // public ApiResponse<Void> InvalidCurrencyExceptionHandler(
  //     InvalidCurrencyException ex) {
  //   return ApiResponse.<Void>builder() //
  //       .code(Syscode.INVALID_CURRENCY.getCode()) //
  //       .message(Syscode.INVALID_CURRENCY.getMessage()) //
  //       .data(null) //
  //       .build();
  // }

  @ExceptionHandler(RestClientException.class)
  @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
  public ApiResponse<Void> RestClientExceptionHandler(RestClientException ex) {
    return ApiResponse.<Void>builder() //
        .code(Syscode.JPH_NOT_AVAILABLE.getCode()) //
        .message(Syscode.JPH_NOT_AVAILABLE.getMessage()) //
        .data(null) //
        .build();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
  public ApiResponse<Void> ExceptionHandler(Exception ex) {
    return ApiResponse.<Void>builder() //
        .code(Syscode.GENERAL_EXCEPTION.getCode()) //
        .message(Syscode.GENERAL_EXCEPTION.getMessage()) //
        .data(null) //
        .build();
  }


}
