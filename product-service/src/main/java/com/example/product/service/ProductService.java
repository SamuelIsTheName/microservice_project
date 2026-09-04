package com.example.product.service;

import com.example.product.dto.CreateProductRequest;
import com.example.product.exception.ProductNotFoundException;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository productRepository){
        this.repository = productRepository;
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
}
