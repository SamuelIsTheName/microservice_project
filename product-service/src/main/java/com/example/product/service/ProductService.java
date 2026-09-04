package com.example.product.service;

import com.example.product.dto.CreateProductRequest;
import com.example.product.dto.InventoryResponse;
import com.example.product.dto.ProductDetailsResponse;
import com.example.product.exception.ProductNotFoundException;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final RestTemplate restTemplate;

    public ProductService(ProductRepository productRepository, RestTemplate restTemplate){
        this.repository = productRepository;
        this.restTemplate = restTemplate;
    }

    public Product createProduct(CreateProductRequest product){

        Product newProduct = new Product(null, product.getName(), product.getPrice());

        return repository.save(newProduct);
    }

    public List<Product> getAllProducts(){
        return repository.findAll();
    }

    public Product getProductById(Long Id){
        return repository.findById(Id)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
    }

    public ProductDetailsResponse getProductDetails(Long id){

        Product product = repository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

        InventoryResponse inventoryResponse = restTemplate.getForObject("http://localhost:8082/inventory/"+ id, InventoryResponse.class);

        return new ProductDetailsResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                inventoryResponse.getQuantity()
        );
    }
}
