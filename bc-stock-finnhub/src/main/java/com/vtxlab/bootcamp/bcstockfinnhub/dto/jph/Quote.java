package com.vtxlab.bootcamp.bcstockfinnhub.dto.jph;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Quote {
  
  // Current Price
  private double c;
  // Change
  private double d;
  // Percent change
  private double dp;
  // High price of the day
  private double h;
  // Low price of the day
  private double l;
  // Open price of the day
  private double o;
  // Previous close price
  private double pc;
  // Timestamp
  private long t;

}
