package com.example.inventory.controller;

import com.example.inventory.model.Inventory;
import com.example.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService service;

    public InventoryController(InventoryService service){
        this.service = service;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getInventory(@PathVariable Long productId){
        Inventory inventory = service.getInventoryByProductId(productId);

        return ResponseEntity.ok(inventory);
    }
}
