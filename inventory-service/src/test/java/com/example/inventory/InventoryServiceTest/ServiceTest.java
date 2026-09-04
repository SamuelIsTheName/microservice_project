package com.example.inventory.InventoryServiceTest;
import com.example.inventory.exception.InventoryNotFoundException;
import com.example.inventory.model.Inventory;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    @Mock
    private InventoryRepository repository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    void getInventoryByProductId_shouldReturnExistingProduct(){
        Long productId = 1L;

        Inventory inventory = new Inventory(productId,20);

        when(repository.findByProductId(productId)).thenReturn(Optional.of(inventory));

        Inventory result = inventoryService.getInventoryByProductId(productId);

        assertEquals(inventory,result);
        assertEquals(20, result.getQuantity());

        verify(repository).findByProductId(productId);
    }

    @Test
    void getInventoryByProductId_shouldThrowExceptionWhenInventoryDoesNotExist() {
        when(repository.findByProductId(1L)) .thenReturn(Optional.empty());

        InventoryNotFoundException exception = assertThrows(
                InventoryNotFoundException.class,
                () -> inventoryService.getInventoryByProductId(1L)
        );

        assertEquals("Inventory not found", exception.getMessage());

        verify(repository).findByProductId(1L); }
}
