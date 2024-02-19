package com.vtxlab.bootcamp.bcstockfinnhub.infra;

import org.springframework.web.util.UriComponentsBuilder;

public class UriCompBuilder {

  public static String url(Scheme scheme, String domain, String basepath,
      String endpoint, String symbol, String key) {
    return UriComponentsBuilder.newInstance() //
        .scheme(scheme.name().toLowerCase()) //
        .host(domain) //
        .path(basepath) //
        .path(endpoint) //
        .queryParam("symbol", symbol) //
        .queryParam("token", key) //
        .toUriString();
  }

}
