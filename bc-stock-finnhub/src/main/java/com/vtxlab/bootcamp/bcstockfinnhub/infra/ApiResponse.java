package com.vtxlab.bootcamp.bcstockfinnhub.infra;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ApiResponse<T> {

  private String code;
  private String message;
  private T data;
  
}
