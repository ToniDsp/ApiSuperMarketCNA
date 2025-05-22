package com.mercadona.kata.hexagonal.driven.mappers;

import com.mercadona.kata.hexagonal.domain.Batch;
import com.mercadona.kata.hexagonal.driven.models.BatchMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BatchMOMapper {

  BatchMOMapper INSTANCE = Mappers.getMapper(BatchMOMapper.class);

  List<Batch> mapToBatchList(List<BatchMO> batchMOList);

  @Mapping(source = "product.productId", target = "productId.productId")
  @Mapping(source = "store.storeId", target = "storeId.storeId")
  Batch mapToDomain(BatchMO batchMO);

  @Mapping(source = "productId.productId", target = "product.productId")
  @Mapping(source = "storeId.storeId", target = "store.storeId")
  BatchMO mapToMO(Batch batch);
}
