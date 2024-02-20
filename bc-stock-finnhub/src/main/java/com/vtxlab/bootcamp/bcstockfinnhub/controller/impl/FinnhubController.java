package com.vtxlab.bootcamp.bcstockfinnhub.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.bcstockfinnhub.controller.FinnhubOperation;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.ApiResponse;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;
import com.vtxlab.bootcamp.bcstockfinnhub.service.FinnhubService;
import com.vtxlab.bootcamp.bcstockfinnhub.service.impl.RedisService;

@RestController
@RequestMapping(value = "/stock/finnhub/api/v1")
public class FinnhubController implements FinnhubOperation {

  @Autowired
  private FinnhubService finnhubService;

  @Autowired
  private RedisService redisService;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public ApiResponse<Quote> getQuote(String symbol) throws JsonProcessingException {

    Quote quoteJph = finnhubService.getQuote(symbol);

    String key = new StringBuilder("stock:finnhub:quote:").append(symbol).toString();

    String jsonString = objectMapper.writeValueAsString(quoteJph);

    redisService.setValue(key, jsonString);

    String redisQuote = redisService.getValue(key);

    Quote quote = objectMapper.readValue(redisQuote, Quote.class);

    return ApiResponse.<Quote>builder() //
        .code(Syscode.OK.getCode()) //
        .message(Syscode.OK.getMessage()) //
        .data(quote) //
        .build();

  }

  @Override
  public ApiResponse<Profile2> getStockProfile(String symbol) throws JsonProcessingException {

    Profile2 profile2Jph = finnhubService.getStockProfile2(symbol);

    String key = new StringBuilder("stock:finnhub:profile2:").append(symbol).toString();

    String jsonString = objectMapper.writeValueAsString(profile2Jph);

    redisService.setValue(key, jsonString);

    String redisProfile = redisService.getValue(key);

    Profile2 profile = objectMapper.readValue(redisProfile, Profile2.class);

    return ApiResponse.<Profile2>builder() //
        .code(Syscode.OK.getCode()) //
        .message(Syscode.OK.getMessage()) //
        .data(profile) //
        .build();

  }



}
