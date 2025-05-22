package com.mercadona.kata.hexagonal.application.ports.driven;

import com.mercadona.kata.hexagonal.domain.Batch;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface BatchRepositoryPort {
    Batch getBatchById(Long id);
    Batch save (Batch batch);
    Batch update(Batch batch);
    void deleteBatch(Long id);
    List<Batch> findAll();
    List<Batch> findNearExpirationBatchesByStore(Long storeId, LocalDate startDate, LocalDate endDate);
    List<Batch> findBatchesByFilters(Long storeId, Long productId, Boolean isDiscounted, Boolean expired, Boolean removed, Pageable pageable);
    Batch getBatchByStore(Long storeCode, Long batchId);

    List<Batch> getBatchesByStore(Long storeId);




}

