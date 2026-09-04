package com.example.inventory.repository;

import com.example.inventory.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    List<Inventory> findAll();
    Optional<Inventory> findByProductId(Long productId);
}
