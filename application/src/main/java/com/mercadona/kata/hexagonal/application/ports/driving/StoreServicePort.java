package com.mercadona.kata.hexagonal.application.ports.driving;

import com.mercadona.kata.hexagonal.domain.Store;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StoreServicePort {

    Store getById(Long id);
    List<Store> getAll();
    Store save(Store store);
    Store update(Store store);
    void delete(Long id);
}


