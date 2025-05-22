package com.mercadona.kata.hexagonal.driving.controllers.adapters;

import com.mercadona.kata.hexagonal.application.ports.driving.StoreServicePort;
import com.mercadona.kata.hexagonal.domain.Store;
import com.mercadona.kata.hexagonal.domain.dto.StoreResponse;
import com.mercadona.kata.hexagonal.driving.controllers.adapters.api.StoreApi;
import com.mercadona.kata.hexagonal.driving.controllers.mappers.StoreDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController implements StoreApi {

  private final StoreServicePort storeService;
  private final StoreDTOMapper storeResponseMapper;

  @Override
  public ResponseEntity<StoreResponse> getStoreById(Long id) {
    return ResponseEntity.ok(
      storeResponseMapper.mapToDto(storeService.getById(id))
    );
  }

  @Override
  public ResponseEntity<List<StoreResponse>> getAllStores() {
    return ResponseEntity.ok(
      storeResponseMapper.mapToDtoList(storeService.getAll())
    );
  }

  @Override
  public ResponseEntity<StoreResponse> createStore(Store store) {
    return ResponseEntity.ok(
      storeResponseMapper.mapToDto(storeService.save(store))
    );
  }

  @Override
  public ResponseEntity<StoreResponse> updateStore(Long id, Store store) {
    store.setStoreId(id);
    return ResponseEntity.ok(
      storeResponseMapper.mapToDto(storeService.update(store))
    );
  }

  @Override
  public ResponseEntity<Void> deleteStore(Long id) {
    storeService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
