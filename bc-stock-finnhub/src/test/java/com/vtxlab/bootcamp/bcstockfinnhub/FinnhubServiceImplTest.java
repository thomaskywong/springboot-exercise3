package com.vtxlab.bootcamp.bcstockfinnhub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Scheme;
import com.vtxlab.bootcamp.bcstockfinnhub.mapper.UriCompBuilder;
import com.vtxlab.bootcamp.bcstockfinnhub.service.impl.FinnhubServiceImpl;

@SpringBootTest
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

  @Autowired
  private FinnhubServiceImpl finnhubServiceImpl;

  @MockBean
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
  void testGetProfile() {

    Profile2 expected = Profile2.builder() //
        .country("US") //
        .currency("USD") //
        .estimateCurrency("USD") //
        .exchange("NASDAQ NMS - GLOBAL MARKET") //
        .finnhubIndustry("Technology") //
        .ipo(LocalDate.parse("1986-03-13")) //
        .logo(
            "https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/MSFT.svg") //
        .marketCapitalization(2992905.3125173687) //
        .name("Microsoft Corp") //
        .phone("14258828080") //
        .shareOutstanding(7430.44) //
        .ticker("MSFT") //
        .weburl("https://www.microsoft.com/en-us") //
        .build();

    String symbol = "MSFT";

    String urlString = UriCompBuilder.url(Scheme.HTTPS, domain, basepath,
        profileEndpoint, symbol, key);

    Mockito.when(restTemplate.getForObject(urlString, Profile2.class))
        .thenReturn(expected);

    Profile2 actual = finnhubServiceImpl.getStockProfile2(symbol);

    assertEquals(expected, actual);

  }


}
