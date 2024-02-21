package com.vtxlab.bootcamp.bcstockfinnhub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.exception.InvalidStockSymbolException;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Scheme;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.UriCompBuilder;
import com.vtxlab.bootcamp.bcstockfinnhub.service.impl.FinnhubServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FinnhubServiceImplTest {

  @Value(value = "${api.jph.domain}")
  private String domain;

  @Value(value = "${api.jph.basepath}")
  private String basepath;

  @Value(value = "${api.jph.endpoints.quote}")
  private String quoteEndpoint;

  @Value(value = "${api.jph.endpoints.profile}")
  private String profileEndpoint;

  @Value(value = "${api.jph.key}")
  private String key;

  @InjectMocks
  private FinnhubServiceImpl finnhubServiceImpl;

  @Mock
  private RestTemplate restTemplate;

  @Test
  void testGetQuote() {

    Quote expected = Quote.builder() //
        .c(182.31) //
        .d(-1.55) //
        .dp(-0.843) //
        .h(184.85) //
        .l(181.665) //
        .o(183.42) //
        .pc(183.86) //
        .t(1708117200) //
        .build();

    String symbol = "AAPL";

    String urlString = UriCompBuilder.url(Scheme.HTTPS, domain, basepath,
        quoteEndpoint, symbol, key);

    Mockito.when(restTemplate.getForObject(urlString, Quote.class))
        .thenReturn(expected);

    Quote actual = finnhubServiceImpl.getQuote(symbol);

    assertEquals(expected, actual);

  }

  @Test
  void testGetQuoteInvalidSymbol() {

    Quote expected = Quote.builder() //
        .c(0) //
        .d(null) //
        .dp(null) //
        .h(0) //
        .l(0) //
        .o(0) //
        .pc(0) //
        .t(0) //
        .build();

    String symbol = "ZZZZ";

    String urlString = UriCompBuilder.url(Scheme.HTTPS, domain, basepath,
        quoteEndpoint, symbol, key);

    assertThrows(InvalidStockSymbolException.class,
        () -> finnhubServiceImpl.getQuote(symbol));

  }

  @Test
  void testGetProfile() {

    Profile2 expected = Profile2.builder() //
        .country("US") //
        .currency("USD") //
        .estimateCurrency("USD") //
        .exchange("NASDAQ NMS - GLOBAL MARKET") //
        .finnhubIndustry("Technology") //
        .ipo(LocalDate.parse("1980-12-12")) //
        .logo(
            "https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/AAPL.svg") //
        .marketCapitalization(14089961010L) //
        .name("Apple Inc") //
        .phone("14089961010") //
        .shareOutstanding(15441.88) //
        .ticker("AAPL") //
        .weburl("https://www.apple.com/") //
        .build();
    String symbol = "AAPL";

    String urlString = UriCompBuilder.url(Scheme.HTTPS, domain, basepath,
        profileEndpoint, symbol, key);

    Mockito.when(restTemplate.getForObject(urlString, Profile2.class))
        .thenReturn(expected);

    Profile2 actual = finnhubServiceImpl.getStockProfile2(symbol);

    assertEquals(expected, actual);

  }

  @Test
  void testGetProfileInvalidSymbol() {

    Profile2 profile = Profile2.builder() //
        .country(null) //
        .currency(null) //
        .estimateCurrency(null) //
        .finnhubIndustry(null) //
        .ipo(null) //
        .marketCapitalization(0.0) //
        .name(null) //
        .phone(null) //
        .shareOutstanding(0.0) //
        .ticker(null) //
        .weburl(null) //
        .build();

    String symbol = "ZZZZ";

    String urlString = UriCompBuilder.url(Scheme.HTTPS, domain, basepath,
        quoteEndpoint, symbol, key);

    assertThrows(InvalidStockSymbolException.class,
        () -> finnhubServiceImpl.getStockProfile2(symbol));

  }



}
