package com.mercadona.kata.hexagonal.driven.repositories;

import com.mercadona.kata.hexagonal.driven.models.ProductMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProductJpaRepository extends JpaRepository<ProductMO, Long> {
}
