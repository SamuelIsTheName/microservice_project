package com.example.inventory.InventoryControllerTest;

import com.example.inventory.controller.InventoryController;
import com.example.inventory.exception.InventoryNotFoundException;
import com.example.inventory.model.Inventory;
import com.example.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(InventoryController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;

    @Test
    void getInventory_shouldReturn200() throws Exception {
        Inventory inventory = new Inventory(1L, 20);

        when(inventoryService.getInventoryByProductId(1L)).thenReturn(inventory);

        mockMvc.perform(
                get("/inventory/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.quantity").value(20));
    }

    @Test
    void getInventory_shouldReturn404WhenInventoryDoesNotExist() throws Exception {
        when(inventoryService.getInventoryByProductId(1L))
                .thenThrow(new InventoryNotFoundException( "Inventory not found" ));

        mockMvc.perform(get("/inventory/1")) .andExpect(status().isNotFound()); }

}
