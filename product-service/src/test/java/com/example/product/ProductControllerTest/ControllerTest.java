package com.example.product.ProductControllerTest;

import com.example.product.controller.ProductController;
import com.example.product.dto.CreateProductRequest;
import com.example.product.model.Product;
import com.example.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ProductController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void getAllProducts_shouldReturn200() throws Exception {

        Product laptop = new Product( 1L, "Laptop", new BigDecimal("999.99") );
        Product phone = new Product( 2L, "Phone", new BigDecimal("899.99") );
        when(productService.getAllProducts()) .thenReturn(List.of(laptop, phone));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].name").value("Phone"));
    }

    @Test
    void createProduct_shouldReturn201() throws Exception {

        Product createdProduct = new Product( 1L, "Phone", new BigDecimal("899.99") );

        when(productService.createProduct(any())).thenReturn(createdProduct);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""" 
                        {
                            "name": "Phone",
                            "price": 899.99
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Phone"))
                .andExpect(jsonPath("$.price").value(899.99));
    }

    @Test
    void createProduct_withInvalidRequest_shouldReturn400() throws Exception {
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""" 
                        { "name": "ab", "price": 0 }
                        """))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getProductById_shouldReturn200() throws Exception {
        Product product = new Product(
                1L,
                "Laptop",
                new BigDecimal("999.99")
        );
        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(999.99));
    }
}
