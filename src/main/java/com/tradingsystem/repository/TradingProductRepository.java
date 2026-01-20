package com.tradingsystem.repository;

import com.tradingsystem.model.TradingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface TradingProductRepository extends JpaRepository<TradingProduct, Long> {
    Optional<TradingProduct> findBySymbol(String symbol);
    Optional<TradingProduct> findByName(String name);
    List<TradingProduct> findByCategory(String category);
}
