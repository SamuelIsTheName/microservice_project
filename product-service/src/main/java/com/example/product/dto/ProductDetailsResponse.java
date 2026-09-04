package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductDetailsResponse {
    private Long Id;
    private String name;
    private BigDecimal price;
    private int quantity;
}
