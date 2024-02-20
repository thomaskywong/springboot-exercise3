package com.vtxlab.bootcamp.bcstockfinnhub;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.vtxlab.bootcamp.bcstockfinnhub.controller.impl.FinnhubController;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.exception.InvalidStockSymbolException;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;
import com.vtxlab.bootcamp.bcstockfinnhub.service.FinnhubService;

@WebMvcTest(FinnhubController.class)
public class FinnhubControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FinnhubService finnhubService;

  @Test
  void testGetQuote() throws Exception {

    Quote quo = Quote.builder() //
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

    Mockito.when(finnhubService.getQuote(symbol)).thenReturn(quo);

    mockMvc.perform(get("/stock/finnhub/api/v1/quote") //
        .param("symbol", "AAPL")) //
        .andExpect(status().isOk()) //
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(jsonPath("$.code").value("000000")) //
        .andExpect(jsonPath("$.message").value("OK.")) //
        .andExpect(jsonPath("$.data.c").value(182.31)) //
        .andExpect(jsonPath("$.data.d").value(-1.55)) //
        .andExpect(jsonPath("$.data.h").value(184.85)) //
        .andExpect(jsonPath("$.data.l").value(181.665)) //
        .andExpect(jsonPath("$.data.pc").value(183.86)) //
        .andExpect(jsonPath("$.data.t").value(1708117200)) //
        .andDo(print());

  }

  @Test
  void testGetQuoteInvalidSymbol() throws Exception {

    String symbol = "ZZ";

    Mockito.when(finnhubService.getQuote(symbol)).thenThrow(InvalidStockSymbolException.class);

    mockMvc.perform(get("/stock/finnhub/api/v1/quote") //
        .param("symbol", "ZZ")) //
        .andExpect(status().isBadRequest()) //
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(jsonPath("$.code").value(Syscode.INVALID_STOCK_SYMBOL.getCode())) //
        .andExpect(jsonPath("$.message").value(Syscode.INVALID_STOCK_SYMBOL.getMessage())) //
        .andExpect(jsonPath("$.data").isEmpty()) //
        .andDo(print());

  }

  @Test
  void testGetStockProfile() throws Exception {

    Profile2 profile = Profile2.builder() //
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

    Mockito.when(finnhubService.getStockProfile2(symbol)).thenReturn(profile);

    mockMvc.perform(get("/stock/finnhub/api/v1/profile2") //
        .param("symbol", "AAPL")) //
        .andExpect(status().isOk()) //
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(jsonPath("$.code").value("000000")) //
        .andExpect(jsonPath("$.message").value("OK.")) //
        .andExpect(jsonPath("$.data.country").value("US")) //
        .andExpect(jsonPath("$.data.currency").value("USD")) //
        .andExpect(jsonPath("$.data.exchange").value("NASDAQ NMS - GLOBAL MARKET")) //
        .andExpect(jsonPath("$.data.finnhubIndustry").value("Technology")) //
        .andExpect(jsonPath("$.data.ipo").value("1980-12-12")) //
        .andExpect(jsonPath("$.data.logo").value("https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/AAPL.svg")) //
        .andExpect(jsonPath("$.data.marketCapitalization").value(14089961010L)) //
        .andExpect(jsonPath("$.data.name").value("Apple Inc")) //
        .andExpect(jsonPath("$.data.phone").value("14089961010")) //
        .andExpect(jsonPath("$.data.shareOutstanding").value(15441.88)) //
        .andExpect(jsonPath("$.data.ticker").value("AAPL")) //
        .andExpect(jsonPath("$.data.weburl").value("https://www.apple.com/")) //
        .andDo(print());

  }

  @Test
  void testGetStockProfileInvalidSymbol() throws Exception {

    String symbol = "ZZ";

    Mockito.when(finnhubService.getStockProfile2(symbol)).thenThrow(InvalidStockSymbolException.class);

    mockMvc.perform(get("/stock/finnhub/api/v1/profile2") //
        .param("symbol", "ZZ")) //
        .andExpect(status().isBadRequest()) //
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(jsonPath("$.code").value(Syscode.INVALID_STOCK_SYMBOL.getCode())) //
        .andExpect(jsonPath("$.message").value(Syscode.INVALID_STOCK_SYMBOL.getMessage())) //
        .andExpect(jsonPath("$.data").isEmpty()) //
        .andDo(print());

  }

}
