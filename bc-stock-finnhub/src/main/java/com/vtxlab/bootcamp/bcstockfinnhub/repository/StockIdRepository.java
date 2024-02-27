package com.vtxlab.bootcamp.bcstockfinnhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vtxlab.bootcamp.bcstockfinnhub.entity.StockIdEntity;

@Repository
public interface StockIdRepository extends JpaRepository<StockIdEntity, Long> {
  
}
