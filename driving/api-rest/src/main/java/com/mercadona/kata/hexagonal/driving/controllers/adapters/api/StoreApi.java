package com.mercadona.kata.hexagonal.driving.controllers.adapters.api;

import com.mercadona.kata.hexagonal.domain.Store;
import com.mercadona.kata.hexagonal.domain.dto.StoreResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Tag(
  name = "store",
  description = "Operations related to Store"
)
@RequestMapping(path = "/stores")
public interface StoreApi {

  @Operation(
    operationId = "getStoreById",
    summary = "Returns a store by ID",
    description = "Returns a store by its ID",
    tags = {"store"},
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Store retrieved successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = StoreResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Store not found",
        content = @Content(mediaType = "application/json")
      )
    }
  )
  @GetMapping("/{id}")
  ResponseEntity<StoreResponse> getStoreById(@PathVariable Long id);

  @Operation(
    operationId = "getAllStores",
    summary = "Returns a list of all stores",
    description = "Returns a list of all stores",
    tags = {"store"},
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Stores retrieved successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = StoreResponse.class)
        )
      )
    }
  )
  @GetMapping
  ResponseEntity<List<StoreResponse>> getAllStores();

  @Operation(
    operationId = "createStore",
    summary = "Creates a new store",
    description = "Creates a new store and returns it",
    tags = {"store"},
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Store created successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = StoreResponse.class)
        )
      )
    }
  )
  @PostMapping
  ResponseEntity<StoreResponse> createStore(@RequestBody Store store);

  @Operation(
    operationId = "updateStore",
    summary = "Updates an existing store",
    description = "Updates an existing store and returns the updated store",
    tags = {"store"},
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Store updated successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = StoreResponse.class)
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Store not found",
        content = @Content(mediaType = "application/json")
      )
    }
  )
  @PutMapping("/{id}")
  ResponseEntity<StoreResponse> updateStore(@PathVariable Long id, @RequestBody Store store);

  @Operation(
    operationId = "deleteStore",
    summary = "Deletes a store",
    description = "Deletes a store by ID",
    tags = {"store"},
    responses = {
      @ApiResponse(
        responseCode = "204",
        description = "Store deleted successfully"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Store not found"
      )
    }
  )
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteStore(@PathVariable Long id);
}
