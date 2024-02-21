package com.vtxlab.bootcamp.bcstockfinnhub.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.jph.Symbol;

@Service
public interface FinnhubService {

  Quote getQuote(String symbol);

  Profile2 getStockProfile2(String symbol);

  List<Symbol> getSymbols();

}
