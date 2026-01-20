package com.trading.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradingProductDTO {

    private Long id;

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Product description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    private String status;

}
