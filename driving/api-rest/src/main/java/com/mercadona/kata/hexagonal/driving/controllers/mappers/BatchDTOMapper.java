package com.mercadona.kata.hexagonal.driving.controllers.mappers;

import com.mercadona.kata.hexagonal.domain.Batch;
import com.mercadona.kata.hexagonal.domain.dto.BatchResponse;
import com.mercadona.kata.hexagonal.domain.Product;
import com.mercadona.kata.hexagonal.domain.Store;
import com.mercadona.kata.hexagonal.application.ports.driven.ProductRepositoryPort;
import com.mercadona.kata.hexagonal.application.ports.driven.StoreRepositoryPort;

import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BatchDTOMapper {

  @Mapping(source = "productId.productId", target = "productId")
  @Mapping(source = "storeId.storeId", target = "storeId")
  BatchResponse toDto(Batch batch);

  List<BatchResponse> toDtoList(List<Batch> batches);

  @Mapping(target = "productId", expression = "java(getProductById(batchResponse.getProductId(), productRepositoryPort))")
  @Mapping(target = "storeId", expression = "java(getStoreById(batchResponse.getStoreId(), storeRepositoryPort))")
  Batch toDomain(BatchResponse batchResponse, @Context ProductRepositoryPort productRepositoryPort, @Context StoreRepositoryPort storeRepositoryPort);

  default Product getProductById(Long id, ProductRepositoryPort port) {
    return port.getProductById(id);
  }

  default Store getStoreById(Long id, StoreRepositoryPort port) {
    return port.getById(id);
  }
}
