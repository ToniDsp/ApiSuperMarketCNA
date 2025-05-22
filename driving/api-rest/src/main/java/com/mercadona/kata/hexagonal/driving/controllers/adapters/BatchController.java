package com.mercadona.kata.hexagonal.driving.controllers.adapters;

import com.mercadona.kata.hexagonal.application.ports.driving.BatchServicePort;
import com.mercadona.kata.hexagonal.domain.Batch;
import com.mercadona.kata.hexagonal.domain.dto.BatchResponse;
import com.mercadona.kata.hexagonal.domain.dto.DiscountStatsResponse;
import com.mercadona.kata.hexagonal.driving.controllers.adapters.api.BatchApi;
import com.mercadona.kata.hexagonal.driving.controllers.mappers.BatchDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path= "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
public class BatchController implements BatchApi {

  private BatchDTOMapper batchDTOMapper;
  private BatchServicePort batchService;

  public BatchController (BatchServicePort batchService, BatchDTOMapper batchDTOMapper){
    this.batchService = batchService;
    this.batchDTOMapper = batchDTOMapper;
  }
    public ResponseEntity<BatchResponse> getBatchById(@PathVariable Long id) {
        Batch batch = batchService.getBatchById(id);
        BatchResponse batchResponse = batchDTOMapper.toDto(batch);
        return ResponseEntity.ok(batchResponse);
    }

    public ResponseEntity<List<BatchResponse>> getAllBatchs() {
        List<Batch> batchList = batchService.getAllBatchs();
        List<BatchResponse> listToDto = batchDTOMapper.toDtoList(batchList);
        return ResponseEntity.ok(listToDto);
    }

    public ResponseEntity<BatchResponse> createBatch(@RequestBody Batch batch) {
        Batch savedBatch = batchService.saveBatch(batch);
        if (savedBatch == null){
            throw new IllegalArgumentException("The object of null");
        }
        BatchResponse batchDto = batchDTOMapper.toDto(savedBatch);
        return ResponseEntity.ok(batchDto);
    }

    public ResponseEntity<BatchResponse> updateBatch(@PathVariable Long id, @RequestBody Batch batch) {
        batch.setBatchId(id);
        Batch updatedBatch = batchService.updateBatch(batch);
        BatchResponse batchToDto = batchDTOMapper.toDto(updatedBatch);
        return ResponseEntity.ok(batchToDto);
    }

    public ResponseEntity<Void> deleteBatch(@PathVariable Long id) {
        batchService.deleteBatch(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<List<BatchResponse>> getNearExpirationBatches(@PathVariable("storeId") Long storeId) {
        List<Batch> batches = batchService.getNearExpirationBatches(storeId);
        List<BatchResponse> response = batches.stream()
                .map(batchDTOMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> applyDiscount(
            @PathVariable("storeCode") Long storeId,
            @PathVariable("batchId") Long batchId){
        boolean success = batchService.applyDiscount(storeId, batchId);
        if(success) {
            return ResponseEntity.ok("Discount applied correctly");
        }else{
            return ResponseEntity.badRequest().body("Discount can't apply");
        }
    }
    public ResponseEntity<List<BatchResponse>> getBatches(
            @PathVariable("storeId") Long storeId,
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam(value = "isDiscounted", required = false) Boolean isDiscounted,
            @RequestParam(value = "expired", required = false) Boolean expired,
            @RequestParam(value = "removed", required = false) Boolean removed,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        List<Batch> batches = batchService.getBatches(storeId, productId, isDiscounted, expired, removed, offset, limit);
        List<BatchResponse> response = batches.stream()
                .map(batchDTOMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    public ResponseEntity <Batch> changeBatchesToRemove (
            @PathVariable ("storeId") Long storeId,
            @PathVariable ("batchId") Long batchId){

        Batch getBatch = batchService.getBatchByStore(storeId, batchId);
        getBatch.setRemoved(true);
        Batch batch = batchService.updateBatch(getBatch);

        if(batch == null){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(batch);

        }
    }
    public ResponseEntity<DiscountStatsResponse> getDiscountStats(@PathVariable("storeId") Long storeId){
        DiscountStatsResponse stats = batchService.getDiscountStatsByStore(storeId);
        return ResponseEntity.ok(stats);
    }
    public ResponseEntity<Void> deleteBatchByStore(@PathVariable Long storeId, @PathVariable Long batchId) {
        try {
            batchService.deleteBatchByStore(storeId, batchId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
