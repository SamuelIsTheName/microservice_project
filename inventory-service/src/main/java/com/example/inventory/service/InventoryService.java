package com.example.inventory.service;

import com.example.inventory.exception.InventoryNotFoundException;
import com.example.inventory.model.Inventory;
import com.example.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository){
        this.repository=repository;
    }

    public List<Inventory>getAllInventory(){
        return repository.findAll();
    }

    public Inventory getInventoryByProductId(Long productId){
        return repository.findByProductId(productId)
                .orElseThrow(()->new InventoryNotFoundException("Inventory not found"));
    }
}
