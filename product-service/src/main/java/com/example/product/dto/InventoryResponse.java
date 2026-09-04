package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryResponse {
    private Long productId;
    private int quantity;
}
