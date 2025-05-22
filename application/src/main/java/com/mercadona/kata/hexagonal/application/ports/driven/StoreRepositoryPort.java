package com.mercadona.kata.hexagonal.application.ports.driven;


import com.mercadona.kata.hexagonal.domain.Store;

import java.util.List;

public interface  StoreRepositoryPort {

    Store getById(Long id);

    Store save(Store store);

    Store update(Store store);

    void delete(Long id);

    List<Store> findAll();
}
