package com.vtxlab.bootcamp.bcstockfinnhub.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.model.StockSymbol;
import com.vtxlab.bootcamp.bcstockfinnhub.service.FinnhubService;
import com.vtxlab.bootcamp.bcstockfinnhub.service.impl.RedisService;
import lombok.Getter;

@Getter
@Component
@EnableScheduling
public class ScheduledConfig {

  private LocalDateTime finnhubUpdatedTime = LocalDateTime.MIN;

  @Autowired
  private RedisService redisService;

  @Autowired
  private FinnhubService finnhubService;

  @Autowired
  private ObjectMapper objectMapper;

  @Scheduled(fixedRate = 30000)
  public void fixedRateTask() {

    try {

      finnhubService.saveStockToRedis();

      this.finnhubUpdatedTime = LocalDateTime.now();
      System.out.println("Update time=" + this.finnhubUpdatedTime);

    } catch (RestClientException | JsonProcessingException  ex) {

    } 

  }

}
