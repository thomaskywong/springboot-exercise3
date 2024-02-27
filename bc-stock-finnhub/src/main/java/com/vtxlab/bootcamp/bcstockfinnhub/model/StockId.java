package com.vtxlab.bootcamp.bcstockfinnhub.model;

import java.util.List;
import java.util.Objects;
import com.vtxlab.bootcamp.bcstockfinnhub.exception.EmptyCoinListException;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class StockId {

  private String stockId;

  public static boolean isValidStockId(List<StockId> stockIds, String symbol)
      throws EmptyCoinListException {
    Objects.requireNonNull(symbol);

    if (stockIds.size() == 0) {
      throw new EmptyCoinListException(Syscode.EMPTY_COIN_LIST);
    }

    for (StockId stockId : stockIds) {
      if (stockId.getStockId().equals(symbol)) {
        return true;
      }
    }
    return false;
  }

}
