package com.mercadona.kata.hexagonal.driving.controllers.mappers;

import com.mercadona.kata.hexagonal.domain.Product;
import com.mercadona.kata.hexagonal.domain.dto.ProductResponse;
import com.mercadona.kata.hexagonal.domain.request.ProductRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductDTOMapper {

  ProductResponse toDto(Product product);
  List<ProductResponse> toDtoList(List<Product> products);
  Product toDomain(ProductRequest productRequest);
}
