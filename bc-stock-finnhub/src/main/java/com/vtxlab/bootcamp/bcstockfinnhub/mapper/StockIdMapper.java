package com.vtxlab.bootcamp.bcstockfinnhub.mapper;

import org.springframework.stereotype.Component;
import com.vtxlab.bootcamp.bcstockfinnhub.entity.StockIdEntity;
import com.vtxlab.bootcamp.bcstockfinnhub.model.StockId;


@Component
public class StockIdMapper {

  
  public StockId mapSymbolId(StockIdEntity entity) {
    return StockId.builder() //
                 .stockId(entity.getStockId()) //
                 .build();
  }

  public StockIdEntity mapSymbolIdEntity(StockId id) {
    return StockIdEntity.builder() //
                 .stockId(id.getStockId()) //
                 .build();
  }
  
  
}
