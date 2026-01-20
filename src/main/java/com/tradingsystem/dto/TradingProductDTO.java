package com.tradingsystem.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Data Transfer Object for Trading Product")
public class TradingProductDTO {

    @Schema(description = "Unique product identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Schema(description = "Name of the trading product", example = "Apple Stock", required = true)
    private String name;

    @NotBlank(message = "Product description is required")
    @Schema(description = "Detailed description of the trading product", 
            example = "Apple Inc. Common Stock - Leading technology company", required = true)
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin("0.01")
    @Schema(description = "Current price of the trading product", example = "150.25", required = true)
    private BigDecimal price;

    @Min(value = 0, message = "Quantity cannot be negative")
    @Schema(description = "Available quantity to trade", example = "100", required = true)
    private Integer quantity;

    @NotBlank(message = "Product category is required")
    @Schema(description = "Category of the trading product", example = "STOCKS", required = true)
    private String category;

    @NotBlank(message = "Symbol/Code is required")
    @Schema(description = "Trading symbol or ticker code", example = "AAPL", required = true)
    private String symbol;

    @Schema(description = "Timestamp when product was created", example = "2026-01-20T17:00:00", 
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when product was last updated", example = "2026-01-20T18:00:00",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    // Constructors
    public TradingProductDTO() {
    }

    public TradingProductDTO(Long id, String name, String description, BigDecimal price,
                             Integer quantity, String category, String symbol,
                             LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.symbol = symbol;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

