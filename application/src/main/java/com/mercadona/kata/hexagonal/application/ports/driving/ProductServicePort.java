package com.mercadona.kata.hexagonal.application.ports.driving;

import com.mercadona.kata.hexagonal.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductServicePort {
  Product getProductById(Long id);

  List<Product> getAllProducts();

  Product saveProduct(Product product);

  Product updateProduct(Product product);

  void deleteProduct(Long id);


}


