package com.vtxlab.bootcamp.bcstockfinnhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.ApiResponse;

public interface FinnhubOperation {

  @GetMapping(value = "/quote")
  @ResponseStatus(value = HttpStatus.OK)
  ApiResponse<Quote> getQuote(@RequestParam(required = true) String symbol) throws JsonProcessingException;
  
  @GetMapping(value = "/profile2")
  @ResponseStatus(value = HttpStatus.OK)
  ApiResponse<Profile2> getStockProfile(@RequestParam(required = true) String symbol) throws JsonProcessingException;
  
  
}
