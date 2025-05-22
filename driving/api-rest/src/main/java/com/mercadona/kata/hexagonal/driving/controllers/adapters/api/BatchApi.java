package com.mercadona.kata.hexagonal.driving.controllers.adapters.api;

import com.mercadona.kata.hexagonal.domain.Batch;
import com.mercadona.kata.hexagonal.domain.dto.BatchResponse;
import com.mercadona.kata.hexagonal.domain.dto.DiscountStatsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Validated
@Tag(name = "batch", description = "Operations related to Batch")
public interface BatchApi {

  @Operation(
    operationId = "getBatchById",
    summary = "Returns a batch by its ID",
    responses = {
      @ApiResponse(responseCode = "200", description = "Batch retrieved successfully",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = BatchResponse.class))),
      @ApiResponse(responseCode = "404", description = "Batch not found")
    }
  )
  @GetMapping("/{id}")
  ResponseEntity<BatchResponse> getBatchById(@PathVariable Long id);

  @Operation(
    operationId = "getAllBatchs",
    summary = "Returns a list of all batches",
    responses = {@ApiResponse(responseCode = "200", description = "Batches retrieved successfully")}
  )
  @GetMapping
  ResponseEntity<List<BatchResponse>> getAllBatchs();

  @Operation(
    operationId = "createBatch",
    summary = "Creates a new Batch",
    responses = {@ApiResponse(responseCode = "200", description = "Batch created successfully")}
  )
  @PostMapping
  ResponseEntity<BatchResponse> createBatch(@RequestBody Batch batch);

  @Operation(
    operationId = "updateBatch",
    summary = "Updates an existing Batch",
    responses = {
      @ApiResponse(responseCode = "200", description = "Batch updated successfully"),
      @ApiResponse(responseCode = "404", description = "Batch not found")
    }
  )
  @PutMapping("/{id}")
  ResponseEntity<BatchResponse> updateBatch(@PathVariable Long id, @RequestBody Batch batch);

  @Operation(
    operationId = "deleteBatch",
    summary = "Deletes an existing Batch",
    responses = {
      @ApiResponse(responseCode = "204", description = "Batch deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Batch not found")
    }
  )
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteBatch(@PathVariable Long id);

  @Operation(
    operationId = "getNearExpirationBatches",
    summary = "Returns batches near expiration for a store",
    responses = {@ApiResponse(responseCode = "200", description = "Batches retrieved successfully")}
  )
  @GetMapping("/stores/{storeId}/batches/")
  ResponseEntity<List<BatchResponse>> getNearExpirationBatches(@PathVariable("storeId") Long storeId);

  @Operation(
    operationId = "applyDiscount",
    summary = "Applies discount to a batch",
    responses = {
      @ApiResponse(responseCode = "200", description = "Discount applied successfully"),
      @ApiResponse(responseCode = "400", description = "Discount can't be applied")
    }
  )
  @PatchMapping("/stores/{storeId}/batches/{batchId}/discount")
  ResponseEntity<String> applyDiscount(@PathVariable("storeId") Long storeId, @PathVariable("batchId") Long batchId);

  @Operation(
    operationId = "getBatches",
    summary = "Get batches by filter",
    responses = {@ApiResponse(responseCode = "200", description = "Batches retrieved successfully")}
  )
  @GetMapping("/stores/{storeId}/batches")
  ResponseEntity<List<BatchResponse>> getBatches(
    @PathVariable("storeId") Long storeId,
    @RequestParam(value = "productId", required = false) Long productId,
    @RequestParam(value = "isDiscounted", required = false) Boolean isDiscounted,
    @RequestParam(value = "expired", required = false) Boolean expired,
    @RequestParam(value = "removed", required = false) Boolean removed,
    @RequestParam(value = "offset", defaultValue = "0") int offset,
    @RequestParam(value = "limit", defaultValue = "10") int limit);

  @Operation(
    operationId = "changeBatchesToRemove",
    summary = "Mark a batch as removed",
    responses = {@ApiResponse(responseCode = "200", description = "Batch updated successfully")}
  )
  @PatchMapping("/stores/{storeId}/batches/{batchId}/remove")
  ResponseEntity<Batch> changeBatchesToRemove(@PathVariable("storeId") Long storeId, @PathVariable("batchId") Long batchId);

  @Operation(
    operationId = "getDiscountStats",
    summary = "Get discount statistics for a store",
    responses = {@ApiResponse(responseCode = "200", description = "Stats retrieved successfully")}
  )
  @GetMapping("/stores/{storeId}/batches/discount-stats")
  ResponseEntity<DiscountStatsResponse> getDiscountStats(@PathVariable("storeId") Long storeId);

  @Operation(
    operationId = "deleteBatchByStore",
    summary = "Delete batch by store and batch ID",
    responses = {
      @ApiResponse(responseCode = "204", description = "Batch deleted successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "403", description = "Operation forbidden")
    }
  )
  @DeleteMapping("/stores/{storeId}/batches/{batchId}")
  ResponseEntity<Void> deleteBatchByStore(@PathVariable Long storeId, @PathVariable Long batchId);
}
