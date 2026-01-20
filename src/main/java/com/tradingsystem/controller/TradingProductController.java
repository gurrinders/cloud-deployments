package com.tradingsystem.controller;

import com.tradingsystem.dto.TradingProductDTO;
import com.tradingsystem.service.TradingProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Trading Products", description = "API endpoints for managing trading products (CRUD operations)")
public class TradingProductController {

    @Autowired
    private TradingProductService service;

    @PostMapping
    @Operation(summary = "Create a new trading product", 
               description = "Creates a new trading product with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = TradingProductDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Product already exists")
    })
    public ResponseEntity<TradingProductDTO> createProduct(
            @Valid @RequestBody TradingProductDTO dto) {
        TradingProductDTO created = service.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Get all trading products", 
               description = "Retrieves a list of all available trading products")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    public ResponseEntity<List<TradingProductDTO>> getAllProducts() {
        List<TradingProductDTO> products = service.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", 
               description = "Retrieves a specific trading product by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found",
                     content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = TradingProductDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<TradingProductDTO> getProductById(
            @Parameter(description = "Product ID", required = true, example = "1")
            @PathVariable Long id) {
        TradingProductDTO product = service.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/symbol/{symbol}")
    @Operation(summary = "Get product by symbol", 
               description = "Retrieves a trading product by its trading symbol (e.g., AAPL, BTC)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found",
                     content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = TradingProductDTO.class))),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<TradingProductDTO> getProductBySymbol(
            @Parameter(description = "Trading symbol", required = true, example = "AAPL")
            @PathVariable String symbol) {
        TradingProductDTO product = service.getProductBySymbol(symbol);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category", 
               description = "Retrieves all trading products in a specific category")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    public ResponseEntity<List<TradingProductDTO>> getProductsByCategory(
            @Parameter(description = "Product category", required = true, example = "STOCKS")
            @PathVariable String category) {
        List<TradingProductDTO> products = service.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a trading product", 
               description = "Updates an existing trading product with new details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully",
                     content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = TradingProductDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<TradingProductDTO> updateProduct(
            @Parameter(description = "Product ID", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody TradingProductDTO dto) {
        TradingProductDTO updated = service.updateProduct(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a trading product", 
               description = "Deletes a trading product from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", required = true, example = "1")
            @PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", 
               description = "Checks if the Trading System service is running")
    @ApiResponse(responseCode = "200", description = "Service is healthy")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Trading System is running");
    }
}
