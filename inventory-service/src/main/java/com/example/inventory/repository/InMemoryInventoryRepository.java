package com.example.inventory.repository;

import com.example.inventory.model.Inventory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryInventoryRepository implements InventoryRepository{

    private final List<Inventory> inventory = new ArrayList<>();

    public InMemoryInventoryRepository(){
        inventory.add(new Inventory(1L,20));
        inventory.add(new Inventory(2L,20));
        inventory.add(new Inventory(3L,20));
    }

    @Override
    public List<Inventory> findAll() {
        return inventory;
    }

    @Override
    public Optional<Inventory> findByProductId(Long productId) {
        return inventory.stream()
                .filter(inventory -> inventory.getProductId().equals(productId))
                .findFirst();
    }
}
