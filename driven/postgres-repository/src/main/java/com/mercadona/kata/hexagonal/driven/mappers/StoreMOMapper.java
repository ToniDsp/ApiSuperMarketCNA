package com.mercadona.kata.hexagonal.driven.mappers;

import com.mercadona.kata.hexagonal.domain.Store;
import com.mercadona.kata.hexagonal.driven.models.StoreMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreMOMapper {

  StoreMOMapper INSTANCE = Mappers.getMapper(StoreMOMapper.class);

  @Mapping(source = "storeId", target = "storeId")
  @Mapping(source = "name", target = "name")
  Store mapToDomain(StoreMO storeMO);

  @Mapping(source = "storeId", target = "storeId")
  @Mapping(source = "name", target = "name")
  StoreMO mapToMO(Store store);

  List<Store> mapToDomainList(List<StoreMO> storeMOList);
}
