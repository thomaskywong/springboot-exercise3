package com.vtxlab.bootcamp.bcstockfinnhub.service;

import org.springframework.stereotype.Service;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;

@Service
public interface FinnhubService {

  Quote getQuote(String symbol);

  Profile2 getStockProfile2(String symbol);

}
