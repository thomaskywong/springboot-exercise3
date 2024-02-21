package com.vtxlab.bootcamp.bcstockfinnhub.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.vtxlab.bootcamp.bcstockfinnhub.exception.FinnhubNotAvailableException;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;

@Service
public class RedisService {
  
  private final RedisTemplate<String, String> redisTemplate;
  
  // @Autowired
  public RedisService(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void setValue(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  public String getValue(String key) {
    String value = redisTemplate.opsForValue().get(key);
    if (value == null) {
      throw new FinnhubNotAvailableException(Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION);
    }
    return value;
  }
  
  
}
