package com.trading.repository;

import com.trading.entity.TradingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradingProductRepository extends JpaRepository<TradingProduct, Long> {
    List<TradingProduct> findByStatus(String status);
    List<TradingProduct> findByCategory(String category);
    List<TradingProduct> findByProductNameContainingIgnoreCase(String productName);
}
