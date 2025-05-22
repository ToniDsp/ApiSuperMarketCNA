package com.mercadona.kata.hexagonal.application.services;

import com.mercadona.kata.hexagonal.application.ports.driven.ProductRepositoryPort;
import com.mercadona.kata.hexagonal.application.ports.driven.StoreRepositoryPort;
import com.mercadona.kata.hexagonal.application.ports.driving.StoreServicePort;
import com.mercadona.kata.hexagonal.domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StoreUseCase implements StoreServicePort {

    StoreRepositoryPort storeRepositoryPort;
  public StoreUseCase (StoreRepositoryPort storeRepositoryPort){
    this.storeRepositoryPort = storeRepositoryPort;
  }

    @Override
    public Store getById(Long id) {
        return storeRepositoryPort.getById(id);
    }

    @Override
    public List<Store> getAll() {
        return storeRepositoryPort.findAll();
    }

    @Override
    public Store save(Store store) {
        return storeRepositoryPort.save(store);
    }

    @Override
    public Store update(Store store) {
        return storeRepositoryPort.save(store);
    }

    @Override
    public void delete(Long id) {
        storeRepositoryPort.delete(id);
    }
}
