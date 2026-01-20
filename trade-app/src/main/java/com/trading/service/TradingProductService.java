package com.trading.service;

import com.trading.dto.TradingProductDTO;
import com.trading.entity.TradingProduct;
import com.trading.repository.TradingProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TradingProductService {

    private final TradingProductRepository repository;

    /**
     * Create a new trading product
     */
    public TradingProductDTO createProduct(TradingProductDTO dto) {
        TradingProduct product = new TradingProduct();
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());

        TradingProduct savedProduct = repository.save(product);
        return convertToDTO(savedProduct);
    }

    /**
     * Get all trading products
     */
    @Transactional(readOnly = true)
    public List<TradingProductDTO> getAllProducts() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get product by ID
     */
    @Transactional(readOnly = true)
    public TradingProductDTO getProductById(Long id) {
        TradingProduct product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return convertToDTO(product);
    }

    /**
     * Get products by status
     */
    @Transactional(readOnly = true)
    public List<TradingProductDTO> getProductsByStatus(String status) {
        return repository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get products by category
     */
    @Transactional(readOnly = true)
    public List<TradingProductDTO> getProductsByCategory(String category) {
        return repository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Search products by name
     */
    @Transactional(readOnly = true)
    public List<TradingProductDTO> searchByProductName(String productName) {
        return repository.findByProductNameContainingIgnoreCase(productName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update trading product
     */
    public TradingProductDTO updateProduct(Long id, TradingProductDTO dto) {
        TradingProduct product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        if (dto.getStatus() != null) {
            product.setStatus(dto.getStatus());
        }

        TradingProduct updatedProduct = repository.save(product);
        return convertToDTO(updatedProduct);
    }

    /**
     * Delete trading product
     */
    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * Convert entity to DTO
     */
    private TradingProductDTO convertToDTO(TradingProduct product) {
        TradingProductDTO dto = new TradingProductDTO();
        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setStatus(product.getStatus());
        return dto;
    }

}
