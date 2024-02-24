package com.vtxlab.bootcamp.bcstockfinnhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  // @Bean
  // ObjectMapper objectMapper() {
  //   return new ObjectMapper();
  // }

}
