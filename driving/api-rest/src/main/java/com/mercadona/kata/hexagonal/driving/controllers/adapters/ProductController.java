package com.mercadona.kata.hexagonal.driving.controllers.adapters;

import com.mercadona.kata.hexagonal.application.ports.driving.ProductServicePort;
import com.mercadona.kata.hexagonal.domain.Product;
import com.mercadona.kata.hexagonal.domain.dto.ProductResponse;
import com.mercadona.kata.hexagonal.domain.request.ProductRequest;
import com.mercadona.kata.hexagonal.driving.controllers.adapters.api.ProductApi;
import com.mercadona.kata.hexagonal.driving.controllers.mappers.ProductDTOMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product", produces = MediaType.APPLICATION_JSON_VALUE)

public class ProductController implements ProductApi {

  private ProductServicePort productService;
  private ProductDTOMapper productDTOMapper;

  public ProductController (ProductServicePort productService, ProductDTOMapper productDTOMapper){
    this.productService = productService;
    this.productDTOMapper = productDTOMapper;
  }

  @Override
  public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
    Product product = productService.getProductById(id);
    ProductResponse productDto = productDTOMapper.toDto(product);
    return ResponseEntity.ok(productDto);
  }

  @Override
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    List<Product> productList = productService.getAllProducts();
    List<ProductResponse> productDto = productDTOMapper.toDtoList(productList);
    return ResponseEntity.ok(productDto);
  }

  @Override
  public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
    Product product = productDTOMapper.toDomain(productRequest);
    Product savedProduct = productService.saveProduct(product);
    ProductResponse productDto = productDTOMapper.toDto(savedProduct);
    return ResponseEntity.ok(productDto);
  }

  @Override
  public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
    Product product = productDTOMapper.toDomain(productRequest);
    product.setProductId(id);
    Product updatedProduct = productService.updateProduct(product);
    ProductResponse productResponse = productDTOMapper.toDto(updatedProduct);
    return ResponseEntity.ok(productResponse);
  }

  @Override
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
