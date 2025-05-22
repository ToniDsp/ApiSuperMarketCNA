package com.mercadona.kata.hexagonal.driven.repositories;

import com.mercadona.kata.hexagonal.driven.models.StoreMO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreJpaRepository extends JpaRepository<StoreMO, Long> {
}
