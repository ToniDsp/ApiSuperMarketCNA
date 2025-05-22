package com.mercadona.kata.hexagonal.driven.repositories;

import com.mercadona.kata.hexagonal.driven.models.BatchMO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BatchJpaRepository extends JpaRepository <BatchMO, Long> {

    @Query("SELECT b FROM BatchMO b WHERE b.store.id = :storeId " +
            "AND b.expirationDate BETWEEN :startDate AND :endDate " +
            "AND b.removed = false AND b.isDiscounted = false")
    List<BatchMO> findNearExpirationBatchesByStore(
            @Param("storeId") Long storeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT b FROM BatchMO b " +
            "WHERE (:storeId IS NULL OR b.store.id = :storeId) " +
            "AND (:productId IS NULL OR b.product.id = :productId) " +
            "AND (:isDiscounted IS NULL OR b.isDiscounted = :isDiscounted) " +
            "AND (:expired IS NULL OR (:expired = true AND b.expirationDate < CURRENT_DATE) OR (:expired = false AND b.expirationDate >= CURRENT_DATE)) " +
            "AND (:removed IS NULL OR b.removed = :removed)")
    List<BatchMO> findBatchesByFilters(
            @Param("storeId") Long storeId,
            @Param("productId") Long productId,
            @Param("isDiscounted") Boolean isDiscounted,
            @Param("expired") Boolean expired,
            @Param("removed") Boolean removed,
            Pageable pageable);

    @Query("SELECT b FROM BatchMO b WHERE b.store.id = :storeId AND b.id = :batchId")
    BatchMO getBatchByStore(@Param("storeId") Long storeId, @Param("batchId") Long batchId);

    @Query("SELECT b FROM BatchMO b WHERE b.store.id = :storeId")
    List<BatchMO> findByStoreId(@Param("storeId") Long storeId);

}
