package com.mercadona.kata.hexagonal.driven.adapters;


import com.mercadona.kata.hexagonal.application.exceptions.ErrorCode;
import com.mercadona.kata.hexagonal.application.exceptions.ProductStoreBatchExceptions;
import com.mercadona.kata.hexagonal.application.ports.driven.ProductRepositoryPort;
import com.mercadona.kata.hexagonal.domain.Product;
import com.mercadona.kata.hexagonal.driven.mappers.ProductMOMapper;
import com.mercadona.kata.hexagonal.driven.models.ProductMO;
import com.mercadona.kata.hexagonal.driven.repositories.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ProductRepositoryAdapter implements ProductRepositoryPort {

  public ProductRepositoryAdapter (ProductMOMapper productMOMapper, ProductJpaRepository productJpaRepository ) {
    this.productMOMapper = productMOMapper;
    this.productJpaRepository = productJpaRepository;
  }

    @Autowired
    ProductMOMapper productMOMapper;

    @Autowired
    ProductJpaRepository productJpaRepository;


    @Override
    public Product getProductById(Long id) {
        ProductMO productMO = productJpaRepository.findById(id)
                .orElseThrow(() -> new ProductStoreBatchExceptions(ErrorCode.ERROR_PRODUCT_DONT_EXIST));
        return productMOMapper.mapToDomain(productMO);
    }

    @Override
    public Product save(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_PRODUCT_NAME);
        }
        try {

            ProductMO productMO = productMOMapper.mapToMO(product);
            ProductMO savedProduct = productJpaRepository.save(productMO);
            return productMOMapper.mapToDomain(savedProduct);
        }
        catch (Exception e){
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_PRODUCT_SAVE);
        }

    }

    @Override
    public Product update(Product product) {
        try {
            ProductMO productMO = productMOMapper.mapToMO(product);
            ProductMO savedProduct = productJpaRepository.save(productMO);
            return productMOMapper.mapToDomain(savedProduct);
        }
        catch (Exception e){
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_PRODUCT_UPDATE);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            productJpaRepository.deleteById(id);
        } catch(Exception e){
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_PRODUCT_DELETE);
        }
    }

    @Override
    public List<Product> findAll() {
        List<ProductMO> listMO = productJpaRepository.findAll();
        if (listMO.isEmpty()) {
            throw new ProductStoreBatchExceptions(ErrorCode.ERROR_PRODUCT_LIST_EMPTY);
        }
        return productMOMapper.mapToDomainList(listMO);
    }

}
