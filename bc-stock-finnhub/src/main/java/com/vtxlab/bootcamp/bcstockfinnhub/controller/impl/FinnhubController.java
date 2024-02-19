package com.vtxlab.bootcamp.bcstockfinnhub.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vtxlab.bootcamp.bcstockfinnhub.controller.FinnhubOperation;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.ApiResponse;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;
import com.vtxlab.bootcamp.bcstockfinnhub.service.FinnhubService;

@RestController
@RequestMapping(value = "/stock/finnhub/api/v1")
public class FinnhubController implements FinnhubOperation {

  @Autowired
  private FinnhubService finnhubService;

  @Override
  public ApiResponse<Quote> getQuote(String symbol) {

    Quote quote = finnhubService.getQuote(symbol);

    return ApiResponse.<Quote>builder() //
        .code(Syscode.OK.getCode()) //
        .message(Syscode.OK.getMessage()) //
        .data(quote) //
        .build();

  }

  @Override
  public ApiResponse<Profile2> getStockProfile(String symbol) {

    Profile2 profile2 = finnhubService.getStockProfile2(symbol);

    return ApiResponse.<Profile2>builder() //
        .code(Syscode.OK.getCode()) //
        .message(Syscode.OK.getMessage()) //
        .data(profile2) //
        .build();

  }



}
