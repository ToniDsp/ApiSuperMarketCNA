package com.mercadona.kata.hexagonal.driven.adapters;

import com.mercadona.kata.hexagonal.application.exceptions.ErrorCode;
import com.mercadona.kata.hexagonal.application.exceptions.ProductStoreBatchExceptions;
import com.mercadona.kata.hexagonal.application.ports.driven.BatchRepositoryPort;
import com.mercadona.kata.hexagonal.domain.Batch;
import com.mercadona.kata.hexagonal.driven.mappers.BatchMOMapper;
import com.mercadona.kata.hexagonal.driven.models.BatchMO;
import com.mercadona.kata.hexagonal.driven.repositories.BatchJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Repository
public class BatchRepositoryAdapter implements BatchRepositoryPort {

    private BatchJpaRepository batchJpaRepository;
    private BatchMOMapper batchMoMapper;

public BatchRepositoryAdapter(BatchJpaRepository batchJpaRepository, BatchMOMapper batchMOMapper){
  this.batchJpaRepository = batchJpaRepository;
  this.batchMoMapper = batchMOMapper;
}

    @Override
    public Batch getBatchById(Long id) {
        BatchMO batchMO = batchJpaRepository.findById(id)
                .orElseThrow(()-> new ProductStoreBatchExceptions(ErrorCode.ERROR_BATCH_DONT_EXIST));
        return batchMoMapper.mapToDomain(batchMO);
    }
  @Override
  public Batch save(Batch batch) {
    if (batch == null) {
      throw new ProductStoreBatchExceptions(ErrorCode.ERROR_BATCH_NULL);
    }
    BatchMO batchMO = batchMoMapper.mapToMO(batch);
    try {
      BatchMO savedMO = batchJpaRepository.save(batchMO);
      return batchMoMapper.mapToDomain(savedMO);
    } catch (DataAccessException | PersistenceException e) {
      throw new ProductStoreBatchExceptions(ErrorCode.ERROR_BATCH_SAVE);
    }
  }

    @Override
    public Batch update(Batch batch) {
        if(batch == null){
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_BATCH_NULL);
        }
        try {
            BatchMO batchMO = batchMoMapper.mapToMO(batch);
            BatchMO updatedMO = batchJpaRepository.save(batchMO);
            return batchMoMapper.mapToDomain(updatedMO);
        }
        catch (Exception e){
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_BATCH_UPDATE);
        }
    }

    @Override
    public void deleteBatch(Long id) {
        try {
            BatchMO batchMO = batchJpaRepository.getReferenceById(id);
            batchJpaRepository.delete(batchMO);
        } catch(Exception e){
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_BATCH_DELETE);
        }
    }
    @Override
    public List<Batch> findAll() {
        List<BatchMO> listBatchMO = batchJpaRepository.findAll();
        return batchMoMapper.mapToBatchList(listBatchMO);
    }

    @Override
    public List<Batch> findNearExpirationBatchesByStore(Long storeId, LocalDate startDate, LocalDate endDate) {
        return batchJpaRepository.findNearExpirationBatchesByStore(storeId, startDate, endDate)
                .stream()
                .map(batchMoMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Batch> findBatchesByFilters(Long storeId, Long productId, Boolean isDiscounted, Boolean expired, Boolean removed, Pageable pageable) {
        return batchJpaRepository.findBatchesByFilters(storeId, productId, isDiscounted, expired, removed, pageable)
                .stream()
                .map(batchMoMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Batch getBatchByStore(Long storeId, Long batchId) {
        BatchMO batchMO = batchJpaRepository.getBatchByStore(storeId, batchId);
        if (batchMO == null) {
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_BATCH_DONT_EXIST);
        }
        return batchMoMapper.mapToDomain(batchMO);
    }


    @Override
    public List<Batch> getBatchesByStore(Long storeId) {
        List<BatchMO> batchMOs = batchJpaRepository.findByStoreId(storeId);
        if (batchMOs.isEmpty()) {
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_BATCHES_NOT_FOUND_FOR_STORE);
        }
        return batchMOs.stream()
                .map(batchMoMapper::mapToDomain)
                .toList();
    }


}
