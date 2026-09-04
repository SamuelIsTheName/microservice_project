package com.example.product.repository;

import com.example.product.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = new ArrayList<>();
    private long nextId = 1;

    public InMemoryProductRepository(){
        products.add(new Product(nextId++,"Laptop",new BigDecimal("999.99")));
        products.add(new Product(nextId++,"Mouse",new BigDecimal("29.99")));
        products.add(new Product(nextId++,"Keyboard",new BigDecimal("79.99")));
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product save(Product product) {
        product.setId(nextId++);
        products.add(product);
        return product;
    }
}
