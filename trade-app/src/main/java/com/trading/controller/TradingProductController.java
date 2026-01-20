package com.trading.controller;

import com.trading.dto.TradingProductDTO;
import com.trading.service.TradingProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class TradingProductController {

    private final TradingProductService productService;

    /**
     * Create a new trading product
     */
    @PostMapping
    public ResponseEntity<TradingProductDTO> createProduct(@Valid @RequestBody TradingProductDTO dto) {
        TradingProductDTO createdProduct = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * Get all trading products
     */
    @GetMapping
    public ResponseEntity<List<TradingProductDTO>> getAllProducts() {
        List<TradingProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Get product by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TradingProductDTO> getProductById(@PathVariable Long id) {
        TradingProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Get products by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TradingProductDTO>> getProductsByStatus(@PathVariable String status) {
        List<TradingProductDTO> products = productService.getProductsByStatus(status);
        return ResponseEntity.ok(products);
    }

    /**
     * Get products by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<TradingProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<TradingProductDTO> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    /**
     * Search products by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<TradingProductDTO>> searchByProductName(@RequestParam String name) {
        List<TradingProductDTO> products = productService.searchByProductName(name);
        return ResponseEntity.ok(products);
    }

    /**
     * Update trading product
     */
    @PutMapping("/{id}")
    public ResponseEntity<TradingProductDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody TradingProductDTO dto) {
        TradingProductDTO updatedProduct = productService.updateProduct(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Delete trading product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
