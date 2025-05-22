package com.mercadona.kata.hexagonal.application.services;


import com.mercadona.kata.hexagonal.application.ports.driven.ProductRepositoryPort;
import com.mercadona.kata.hexagonal.application.ports.driving.ProductServicePort;
import com.mercadona.kata.hexagonal.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUseCase implements ProductServicePort {

  ProductRepositoryPort productRepositoryPort;

  public ProductUseCase (ProductRepositoryPort productRepositoryPort){
  this.productRepositoryPort = productRepositoryPort;
  }

    @Override
    public Product getProductById(Long id) {
        return productRepositoryPort.getProductById(id);
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepositoryPort.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepositoryPort.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepositoryPort.update(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepositoryPort.deleteById(id);
    }
}

