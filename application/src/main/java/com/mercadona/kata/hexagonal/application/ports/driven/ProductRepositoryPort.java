package com.mercadona.kata.hexagonal.application.ports.driven;


import com.mercadona.kata.hexagonal.domain.Product;

import java.util.List;

public interface ProductRepositoryPort {
    Product getProductById(Long id);
    Product save(Product product);
    Product update (Product product);
    void deleteById(Long id);
    List<Product> findAll();


}

