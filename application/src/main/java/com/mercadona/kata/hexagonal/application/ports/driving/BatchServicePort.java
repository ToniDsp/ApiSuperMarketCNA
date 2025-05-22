package com.mercadona.kata.hexagonal.application.ports.driving;

import com.mercadona.kata.hexagonal.domain.Batch;
import com.mercadona.kata.hexagonal.domain.dto.DiscountStatsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BatchServicePort {

    Batch getBatchById(Long id);
    List<Batch> getAllBatchs();
    Batch saveBatch(Batch batch);
    Batch updateBatch(Batch batch);
    void deleteBatch(Long id);
    List<Batch> getNearExpirationBatches(Long batchId);
    boolean applyDiscount(Long storeId, Long batchId);
    List<Batch> getBatches(Long storeId, Long productId, Boolean isDiscounted, Boolean expired, Boolean removed, int offset, int limit);
    Batch getBatchByStore(Long storeId, Long batchId);
    DiscountStatsResponse getDiscountStatsByStore(Long storeId);
    void deleteBatchByStore(Long storeId, Long batchId);


}
