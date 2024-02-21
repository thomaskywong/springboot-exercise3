package com.vtxlab.bootcamp.bcstockfinnhub.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Symbol;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Scheme;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.UriCompBuilder;
import com.vtxlab.bootcamp.bcstockfinnhub.service.FinnhubService;

@Service
public class FinnhubServiceImpl implements FinnhubService {

  @Value(value = "${api.jph.domain}")
  private String domain;

  @Value(value = "${api.jph.basepath}")
  private String basepath;

  @Value(value = "${api.jph.endpoints.quote}")
  private String quoteEndpoint;

  @Value(value = "${api.jph.endpoints.profile}")
  private String profileEndpoint;

  @Value(value = "${api.jph.endpoints.symbol}")
  private String symbolsEndpoint;

  @Value(value = "${api.jph.key}")
  private String key;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public Quote getQuote(String symbol) {

    String urlString = UriCompBuilder.url(Scheme.HTTPS, domain, basepath,
        quoteEndpoint, symbol, key);
    // log.info("urlString "+urlString);
    Quote quote = restTemplate.getForObject(urlString, Quote.class);
    // log.info("Quote " + quote);

    return quote;
  }

  @Override
  public Profile2 getStockProfile2(String symbol) {

    String urlString = UriCompBuilder.url(Scheme.HTTPS, domain, basepath,
        profileEndpoint, symbol, key);

    Profile2 profile = restTemplate.getForObject(urlString, Profile2.class);

    return profile;


  }

  @Override
  public List<Symbol> getSymbols() {
    String urlString = UriCompBuilder.url(Scheme.HTTPS, domain, basepath,
        symbolsEndpoint, key);

    Symbol[] symbols = restTemplate.getForObject(urlString, Symbol[].class);
    return Arrays.stream(symbols).collect(Collectors.toList());

  }

}
