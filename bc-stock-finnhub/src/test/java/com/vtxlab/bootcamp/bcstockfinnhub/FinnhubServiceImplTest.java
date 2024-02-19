package com.vtxlab.bootcamp.bcstockfinnhub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
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

    String symbol = "ZZ";

    String urlString = UriCompBuilder.url(Scheme.HTTPS, domain, basepath,
        quoteEndpoint, symbol, key);

    Mockito.when(restTemplate.getForObject(urlString, Quote.class))
        .thenReturn(expected);

    assertThrows(InvalidStockSymbolException.class, () -> finnhubServiceImpl.getQuote(symbol));

  }


}
