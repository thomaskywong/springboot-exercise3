package com.vtxlab.bootcamp.bcstockfinnhub.controller.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.bcstockfinnhub.config.ScheduledConfig;
import com.vtxlab.bootcamp.bcstockfinnhub.controller.FinnhubOperation;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Symbol;
import com.vtxlab.bootcamp.bcstockfinnhub.exception.InvalidStockSymbolException;
import com.vtxlab.bootcamp.bcstockfinnhub.exception.FinnhubNotAvailableException;
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
  private ScheduledConfig scheduleConfig;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public ApiResponse<Quote> getQuote(String symbol)
      throws JsonProcessingException {

    // List<Symbol> symbols = finnhubService.getSymbols();

    // if (!(Symbol.isValidSymbol(symbols, symbol))) {
    //   throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
    // }

    if (!(Symbol.isValidSymbol(symbol))) {
    throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
    }

    Duration duration =
        Duration.between(scheduleConfig.getFinnhubUpdatedTime(), LocalDateTime.now());
    System.out.println("Delay time= " + duration.getSeconds());

    if (duration.getSeconds() > 60) {
      throw new FinnhubNotAvailableException(Syscode.JPH_NOT_AVAILABLE);
    }

    String key =
        new StringBuilder("stock:finnhub:quote:").append(symbol).toString();
    String redisQuote = redisService.getValue(key);
    Quote quote = objectMapper.readValue(redisQuote, Quote.class);

    return ApiResponse.<Quote>builder() //
        .code(Syscode.OK.getCode()) //
        .message(Syscode.OK.getMessage()) //
        .data(quote) //
        .build();
  }

  @Override
  public ApiResponse<Profile2> getStockProfile(String symbol)
      throws JsonProcessingException {

    // List<Symbol> symbols = finnhubService.getSymbols();

    // if (!(Symbol.isValidSymbol(symbols, symbol))) {
    //   throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
    // }

    if (!(Symbol.isValidSymbol(symbol))) {
    throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
    }

    Duration duration =
        Duration.between(scheduleConfig.getFinnhubUpdatedTime(), LocalDateTime.now());
    System.out.println("Delay time= " + duration.getSeconds());


    if (duration.getSeconds() > 60) {
      throw new FinnhubNotAvailableException(Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION);
    }

    String key =
        new StringBuilder("stock:finnhub:profile2:").append(symbol).toString();
    String redisProfile = redisService.getValue(key);
    Profile2 profile = objectMapper.readValue(redisProfile, Profile2.class);

    return ApiResponse.<Profile2>builder() //
        .code(Syscode.OK.getCode()) //
        .message(Syscode.OK.getMessage()) //
        .data(profile) //
        .build();

  }

  @Override
  public ApiResponse<List<Symbol>> getSymbols() throws JsonProcessingException {

    List<Symbol> symbols = finnhubService.getSymbols();

    return ApiResponse.<List<Symbol>>builder() //
        .code(Syscode.OK.getCode()) //
        .message(Syscode.OK.getMessage()) //
        .data(symbols) //
        .build();
  }



}
