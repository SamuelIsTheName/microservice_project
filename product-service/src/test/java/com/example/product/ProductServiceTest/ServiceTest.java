package com.example.product.ProductServiceTest;

import com.example.product.dto.CreateProductRequest;
import com.example.product.dto.InventoryResponse;
import com.example.product.dto.ProductDetailsResponse;
import com.example.product.exception.ProductNotFoundException;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
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
    private ProductRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_shouldCreateProduct(){

        CreateProductRequest request = new CreateProductRequest();
        request.setName("Phone");
        request.setPrice(new BigDecimal("899.99"));

        Product savedProduct = new Product(1L,"Phone",new BigDecimal("899.99"));

        when(repository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.createProduct(request);

        assertEquals(savedProduct,result);
        assertEquals("Phone",result.getName());
        assertEquals(new BigDecimal("899.99"),result.getPrice());

        verify(repository).save(any(Product.class));
    }

    @Test
    void getProductById_shouldReturnExistingProduct(){
        Long productId = 1L;

        Product product = new Product(productId,"Phone",new BigDecimal("899.99"));

        when(repository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);

        assertEquals(product,result);

        verify(repository).findById(productId);
    }

    @Test
    void getProductById_shouldThrowExceptionWhenProductDoesNotExist() {
        Long productId = 1L;

        when(repository.findById(productId)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(productId)
        );

        assertEquals("Product not found",exception.getMessage());

        verify(repository).findById(productId);
    }

    @Test
    void getProductDetails_shouldCombineProductAndInventory() {
        Product product = new Product( 1L, "Laptop", new BigDecimal("1200.00") );
        InventoryResponse inventory = new InventoryResponse( 1L, 20 );

        when(repository.findById(1L)) .thenReturn(Optional.of(product));
        when(restTemplate.getForObject( "http://localhost:8082/inventory/1", InventoryResponse.class )).thenReturn(inventory);

        ProductDetailsResponse result = productService.getProductDetails(1L);

        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(new BigDecimal("1200.00"), result.getPrice());
        assertEquals(20, result.getQuantity());

        verify(repository).findById(1L);
        verify(restTemplate).getForObject( "http://localhost:8082/inventory/1", InventoryResponse.class ); }
}
