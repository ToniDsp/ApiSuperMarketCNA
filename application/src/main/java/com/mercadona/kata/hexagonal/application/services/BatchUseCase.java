package com.mercadona.kata.hexagonal.application.services;


import com.mercadona.kata.hexagonal.application.ports.driven.BatchRepositoryPort;
import com.mercadona.kata.hexagonal.application.ports.driving.BatchServicePort;
import com.mercadona.kata.hexagonal.domain.Batch;
import com.mercadona.kata.hexagonal.domain.dto.DiscountStatsResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BatchUseCase implements BatchServicePort {

    BatchRepositoryPort batchRepositoryPort;

  public BatchUseCase(BatchRepositoryPort batchRepositoryPort){
    this.batchRepositoryPort = batchRepositoryPort;
  }

    @Override
    public Batch getBatchById(Long id) {
        return batchRepositoryPort.getBatchById(id);
    }

    @Override
    public List<Batch> getAllBatchs() {
        return batchRepositoryPort.findAll();
    }

    @Override
    public Batch saveBatch(Batch batch) {
        LocalDate today = LocalDate.now();
        if (batch.getExpirationDate().isBefore(today)) {
            throw new IllegalArgumentException("The expiration date must be later than today's date.");
        }
        if (productoDuplicado(batch)){
            throw new IllegalArgumentException("Duplicate product entry.");
        }
        return batchRepositoryPort.save(batch);
    }

    @Override
    public Batch updateBatch(Batch batch) {
        return batchRepositoryPort.update(batch);
    }

    @Override
    public void deleteBatch(Long id) {
        batchRepositoryPort.deleteBatch(id);
    }

    @Override
    public List<Batch> getNearExpirationBatches(Long storeId) {
        LocalDate today = LocalDate.now();
        LocalDate maxExpirationDate = today.plusDays(2);
        return batchRepositoryPort.findNearExpirationBatchesByStore(storeId, today, maxExpirationDate);
    }

    @Override
    public boolean applyDiscount(Long storeId, Long batchId) {
        Batch batch = batchRepositoryPort.getBatchById(batchId);

        if (batch == null || !batch.getStoreId().getStoreId().equals(storeId)) {
            return false;
        }

        LocalDate today = LocalDate.now();

        boolean puedeDescontar =
                !batch.getExpirationDate().isBefore(today) &&
                        !batch.isDiscounted() &&
                        !batch.isRemoved();

        if (puedeDescontar) {
            batch.setDiscounted(true);
            batch.setDiscountDate(today);
            batchRepositoryPort.update(batch);
            return true;
        }
        return false;
    }

    @Override
    public List<Batch> getBatches(Long storeId, Long productId, Boolean isDiscounted, Boolean expired, Boolean removed, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return batchRepositoryPort.findBatchesByFilters(storeId, productId, isDiscounted, expired, removed, pageable);
    }

    @Override
    public Batch getBatchByStore(Long storeId, Long batchId) {
        LocalDate today = LocalDate.now();
        Batch batch = batchRepositoryPort.getBatchByStore(storeId, batchId);
        if (batch.isRemoved()){
            throw new IllegalArgumentException("The batch is marked as removed.");
        }
        if (batch.getExpirationDate().isBefore(today)){
            throw new IllegalArgumentException("The batch has already expired.");
        }
        return batch;

    }


    public boolean productoDuplicado(Batch batch) {
        List<Batch> listaBatch = batchRepositoryPort.findAll();
        for (Batch batchs : listaBatch) {
            if (batch.getProductId().getProductId().equals(batchs.getProductId().getProductId()) && batch.getEntryDate().equals(batchs.getEntryDate())) {
                return true;
            }
        }
        return false;
    }
    public DiscountStatsResponse getDiscountStatsByStore(Long storeId){
        List<Batch> batches = batchRepositoryPort.getBatchesByStore(storeId);

        long totalDiscounted = batches.stream()
                .filter(Batch::isDiscounted)
                .count();

        long uniqueDiscountedProducts = batches.stream()
                .filter(Batch::isDiscounted)
                .map(batch->batch.getProductId().getProductId())
                .distinct()
                .count();

        Optional<LocalDate> lastDiscountedDate = batches.stream()
                .filter(Batch::isDiscounted)
                .map(Batch::getDiscountDate)
                .max(Comparator.naturalOrder());

        return new DiscountStatsResponse(
                totalDiscounted,
                uniqueDiscountedProducts,
                lastDiscountedDate.orElse(null)
        );

    }
    public void deleteBatchByStore(Long storeId, Long batchId) {
        Batch batch = batchRepositoryPort.getBatchByStore(storeId, batchId);
        if (batch == null) {
            throw new IllegalArgumentException("Batch not found.");
        }
        if (batch.isDiscounted()) {
            throw new IllegalStateException("Discounted batch cannot be deleted.");
        }
        if (batch.isRemoved()) {
            throw new IllegalArgumentException("The batch cannot be deleted because it is already marked as removed."
            );
        }
        if (batch.getExpirationDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The batch can't be removed, because is expiration");
        }
        batchRepositoryPort.deleteBatch(batchId);
    }



}
