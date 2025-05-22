package com.mercadona.kata.hexagonal.driving.controllers.mappers;


import com.mercadona.kata.hexagonal.domain.Store;
import com.mercadona.kata.hexagonal.domain.dto.StoreResponse;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StoreDTOMapper {

    StoreResponse mapToDto(Store store);
    List<StoreResponse> mapToDtoList (List<Store> stores);

}
