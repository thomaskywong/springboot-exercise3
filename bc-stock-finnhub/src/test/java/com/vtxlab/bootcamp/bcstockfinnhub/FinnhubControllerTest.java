package com.vtxlab.bootcamp.bcstockfinnhub;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.bcstockfinnhub.config.ScheduledConfig;
import com.vtxlab.bootcamp.bcstockfinnhub.controller.impl.FinnhubController;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;
import com.vtxlab.bootcamp.bcstockfinnhub.service.FinnhubService;
import com.vtxlab.bootcamp.bcstockfinnhub.service.impl.RedisService;

@WebMvcTest(FinnhubController.class)
public class FinnhubControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private ObjectMapper objectMapper;

  @MockBean
  private FinnhubService finnhubService;

  @MockBean
  private RedisService redisService;

  @MockBean
  private ScheduledConfig scheduleConfig;

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

    String quoteJson = objectMapper.writeValueAsString(quo);

    String symbol = "AAPL";
    String key =
        new StringBuilder("stock:finnhub:quote:").append(symbol).toString();

    LocalDateTime mockNow = LocalDateTime.now();
    LocalDateTime mockTime = mockNow.minusSeconds(60);

    Mockito.when(scheduleConfig.getFinnhubUpdatedTime()).thenReturn(mockTime);

    Mockito.when(redisService.getValue(key)).thenReturn(quoteJson);

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
  void testGetQuoteTimeOut() throws Exception {

    LocalDateTime mockNow = LocalDateTime.now();
    LocalDateTime mockTime = mockNow.minusSeconds(61);

    Mockito.when(scheduleConfig.getFinnhubUpdatedTime()).thenReturn(mockTime);

    mockMvc.perform(get("/stock/finnhub/api/v1/quote") //
        .param("symbol", "AAPL")) //
        .andExpect(status().isServiceUnavailable()) //
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(jsonPath("$.code")
            .value(Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION.getCode())) //
        .andExpect(jsonPath("$.message")
            .value(Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION.getMessage())) //
        .andExpect(jsonPath("$.data").isEmpty()) //
        .andDo(print());

  }

  @Test
  void testGetQuoteInvalidSymbol() throws Exception {

    String symbol = "ZZZZ";
    String key =
        new StringBuilder("stock:finnhub:quote:").append(symbol).toString();

    LocalDateTime mockNow = LocalDateTime.now();
    LocalDateTime mockTime = mockNow.minusSeconds(60);

    Mockito.when(scheduleConfig.getFinnhubUpdatedTime()).thenReturn(mockTime);

    Mockito.when(redisService.getValue(key)).thenReturn(null);

    mockMvc.perform(get("/stock/finnhub/api/v1/quote") //
        .param("symbol", "ZZZZ")) //
        .andExpect(status().isServiceUnavailable()) //
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(jsonPath("$.code")
            .value(Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION.getCode())) //
        .andExpect(jsonPath("$.message")
            .value(Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION.getMessage())) //
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

    String profileJson = objectMapper.writeValueAsString(profile);

    String symbol = "AAPL";
    String key =
        new StringBuilder("stock:finnhub:profile2:").append(symbol).toString();

    LocalDateTime mockNow = LocalDateTime.now();
    LocalDateTime mockTime = mockNow.minusSeconds(60);

    Mockito.when(scheduleConfig.getFinnhubUpdatedTime()).thenReturn(mockTime);

    Mockito.when(redisService.getValue(key)).thenReturn(profileJson);

    mockMvc.perform(get("/stock/finnhub/api/v1/profile2") //
        .param("symbol", "AAPL")) //
        .andExpect(status().isOk()) //
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(jsonPath("$.code").value("000000")) //
        .andExpect(jsonPath("$.message").value("OK.")) //
        .andExpect(jsonPath("$.data.country").value("US")) //
        .andExpect(jsonPath("$.data.currency").value("USD")) //
        .andExpect(
            jsonPath("$.data.exchange").value("NASDAQ NMS - GLOBAL MARKET")) //
        .andExpect(jsonPath("$.data.finnhubIndustry").value("Technology")) //
        .andExpect(jsonPath("$.data.ipo").value("1980-12-12")) //
        .andExpect(jsonPath("$.data.logo").value(
            "https://static2.finnhub.io/file/publicdatany/finnhubimage/stock_logo/AAPL.svg")) //
        .andExpect(jsonPath("$.data.marketCapitalization").value(14089961010L)) //
        .andExpect(jsonPath("$.data.name").value("Apple Inc")) //
        .andExpect(jsonPath("$.data.phone").value("14089961010")) //
        .andExpect(jsonPath("$.data.shareOutstanding").value(15441.88)) //
        .andExpect(jsonPath("$.data.ticker").value("AAPL")) //
        .andExpect(jsonPath("$.data.weburl").value("https://www.apple.com/")) //
        .andDo(print());

  }

  @Test
  void testGetStockProfileTimeOut() throws Exception {

    LocalDateTime mockNow = LocalDateTime.now();
    LocalDateTime mockTime = mockNow.minusSeconds(61);

    Mockito.when(scheduleConfig.getFinnhubUpdatedTime()).thenReturn(mockTime);

    mockMvc.perform(get("/stock/finnhub/api/v1/profile2") //
        .param("symbol", "AAPL")) //
        .andExpect(status().isServiceUnavailable()) //
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(jsonPath("$.code")
            .value(Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION.getCode())) //
        .andExpect(jsonPath("$.message")
            .value(Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION.getMessage())) //
        .andExpect(jsonPath("$.data").isEmpty()) //
        .andDo(print());

  }

  @Test
  void testGetStockProfileInvalidSymbol() throws Exception {

    String symbol = "ZZZZ";
    String key =
        new StringBuilder("stock:finnhub:profile2:").append(symbol).toString();

    LocalDateTime mockNow = LocalDateTime.now();
    LocalDateTime mockTime = mockNow.minusSeconds(60);

    Mockito.when(scheduleConfig.getFinnhubUpdatedTime()).thenReturn(mockTime);

    Mockito.when(redisService.getValue(key)).thenReturn(null);

    mockMvc.perform(get("/stock/finnhub/api/v1/profile2") //
        .param("symbol", "ZZZZ")) //
        .andExpect(status().isServiceUnavailable()) //
        .andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
        .andExpect(jsonPath("$.code")
            .value(Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION.getCode())) //
        .andExpect(jsonPath("$.message")
            .value(Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION.getMessage())) //
        .andExpect(jsonPath("$.data").isEmpty()) //
        .andDo(print());

  }

}
