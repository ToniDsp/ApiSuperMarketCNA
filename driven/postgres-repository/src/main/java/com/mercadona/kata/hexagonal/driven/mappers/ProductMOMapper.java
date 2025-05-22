package com.mercadona.kata.hexagonal.driven.mappers;

import com.mercadona.kata.hexagonal.domain.Product;
import com.mercadona.kata.hexagonal.driven.models.ProductMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMOMapper {

  ProductMOMapper INSTANCE = Mappers.getMapper(ProductMOMapper.class);

  @Mapping(source = "productId", target = "productId")
  @Mapping(source = "name", target = "name")
  Product mapToDomain(ProductMO productMO);

  @Mapping(source = "productId", target = "productId")
  @Mapping(source = "name", target = "name")
  ProductMO mapToMO(Product product);

  List<Product> mapToDomainList(List<ProductMO> products);
}
