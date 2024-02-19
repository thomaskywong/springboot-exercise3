package com.vtxlab.bootcamp.bcstockfinnhub.exception;

import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;

public class InvalidCurrencyException extends IllegalArgumentException {

  public InvalidCurrencyException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
