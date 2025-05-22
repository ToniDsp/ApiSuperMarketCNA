package com.mercadona.kata.hexagonal.driven.adapters;

import com.mercadona.kata.hexagonal.application.exceptions.ErrorCode;
import com.mercadona.kata.hexagonal.application.exceptions.ProductStoreBatchExceptions;
import com.mercadona.kata.hexagonal.application.ports.driven.StoreRepositoryPort;
import com.mercadona.kata.hexagonal.domain.Store;
import com.mercadona.kata.hexagonal.driven.mappers.StoreMOMapper;
import com.mercadona.kata.hexagonal.driven.models.StoreMO;
import com.mercadona.kata.hexagonal.driven.repositories.StoreJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class StoreRepositoryAdapter implements StoreRepositoryPort {

  private StoreJpaRepository storeJpaRepository;
  private StoreMOMapper storeMOMapper;

  public StoreRepositoryAdapter(StoreJpaRepository storeJpaRepository, StoreMOMapper storeMOMapper){
    this.storeJpaRepository = storeJpaRepository;
    this.storeMOMapper = storeMOMapper;
  }

    @Override
    public Store getById(Long id) {
        StoreMO storeMO = storeJpaRepository.findById(id)
                .orElseThrow(() -> new ProductStoreBatchExceptions(ErrorCode.ERROR_STORE_DONT_EXIST));
        return storeMOMapper.mapToDomain(storeMO);
    }

    @Override
    public Store save(Store store) {
        if(store.getName() == null || store.getName().isBlank()){
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_STORE_NAME);
        }
        try{
            StoreMO storeMO = storeMOMapper.mapToMO(store);
            StoreMO savedStoreMO = storeJpaRepository.save(storeMO);
            return storeMOMapper.mapToDomain(savedStoreMO);
        }
        catch (Exception e){
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_STORE_SAVE);
        }
    }

    @Override
    public Store update(Store store) {
        if(store.getName() == null || store.getName().isBlank()){
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_STORE_NAME);
        }
        try {
            StoreMO storeMO = storeMOMapper.mapToMO(store);
            StoreMO updatedStoreMO = storeJpaRepository.save(storeMO);
            return storeMOMapper.mapToDomain(updatedStoreMO);
        }catch (Exception e){
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_STORE_UPDATE);
        }

    }

    @Override
    public void delete(Long id) {
        try {
            storeJpaRepository.deleteById(id);
        } catch(Exception e){
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_STORE_DELETE);
        }
    }

    @Override
    public List<Store> findAll() {
        if(storeMOMapper.mapToDomainList(storeJpaRepository.findAll()).isEmpty()){
            throw new ProductStoreBatchExceptions((ErrorCode.ERROR_STORE_LIST_EMPTY));
        }
        return storeMOMapper.mapToDomainList(storeJpaRepository.findAll());
    }
}
