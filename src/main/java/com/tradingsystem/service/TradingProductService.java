package com.tradingsystem.service;

import com.tradingsystem.dto.TradingProductDTO;
import com.tradingsystem.model.TradingProduct;
import com.tradingsystem.repository.TradingProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TradingProductService {

    @Autowired
    private TradingProductRepository repository;

    public TradingProductDTO createProduct(TradingProductDTO dto) {
        TradingProduct product = new TradingProduct();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setSymbol(dto.getSymbol());
        
        TradingProduct saved = repository.save(product);
        return convertToDTO(saved);
    }

    public TradingProductDTO getProductById(Long id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public TradingProductDTO getProductBySymbol(String symbol) {
        return repository.findBySymbol(symbol)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Product not found with symbol: " + symbol));
    }

    public List<TradingProductDTO> getAllProducts() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TradingProductDTO> getProductsByCategory(String category) {
        return repository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TradingProductDTO updateProduct(Long id, TradingProductDTO dto) {
        TradingProduct product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(dto.getCategory());
        product.setSymbol(dto.getSymbol());

        TradingProduct updated = repository.save(product);
        return convertToDTO(updated);
    }

    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private TradingProductDTO convertToDTO(TradingProduct product) {
        TradingProductDTO dto = new TradingProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setCategory(product.getCategory());
        dto.setSymbol(product.getSymbol());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }
}
