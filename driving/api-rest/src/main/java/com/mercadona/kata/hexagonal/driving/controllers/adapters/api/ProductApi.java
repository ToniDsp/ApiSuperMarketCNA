package com.mercadona.kata.hexagonal.driving.controllers.adapters.api;

import com.mercadona.kata.hexagonal.domain.dto.ProductResponse;
import com.mercadona.kata.hexagonal.domain.request.ProductRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Tag(
  name = "product",
  description = "Operations related to Product"
)
public interface ProductApi {

  @Operation(
    operationId = "getProductById",
    summary = "Returns a product by ID",
    description = "Returns a product by its ID",
    tags = {"product"},
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Product retrieved successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(
            implementation = ProductResponse.class
          )
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Product not found",
        content = @Content(
          mediaType = "application/json"
        )
      )
    }
  )
  @GetMapping("/{id}")
  ResponseEntity<ProductResponse> getProductById(@PathVariable Long id);

  @Operation(
    operationId = "getAllProducts",
    summary = "Returns a list of all products",
    description = "Returns a list of all products",
    tags = {"product"},
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Products retrieved successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(
            implementation = ProductResponse.class
          )
        )
      )
    }
  )
  @GetMapping
  ResponseEntity<List<ProductResponse>> getAllProducts();

  @Operation(
    operationId = "createProduct",
    summary = "Creates a new product",
    description = "Creates a new product and returns it",
    tags = {"product"},
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Product created successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(
            implementation = ProductResponse.class
          )
        )
      )
    }
  )
  @PostMapping
  ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest);

  @Operation(
    operationId = "updateProduct",
    summary = "Updates an existing product",
    description = "Updates an existing product and returns the updated product",
    tags = {"product"},
    responses = {
      @ApiResponse(
        responseCode = "200",
        description = "Product updated successfully",
        content = @Content(
          mediaType = "application/json",
          schema = @Schema(
            implementation = ProductResponse.class
          )
        )
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Product not found",
        content = @Content(
          mediaType = "application/json"
        )
      )
    }
  )
  @PutMapping("/{id}")
  ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest);

  @Operation(
    operationId = "deleteProduct",
    summary = "Deletes a product",
    description = "Deletes a product by ID",
    tags = {"product"},
    responses = {
      @ApiResponse(
        responseCode = "204",
        description = "Product deleted successfully"
      ),
      @ApiResponse(
        responseCode = "404",
        description = "Product not found"
      )
      }
  )
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteProduct(@PathVariable Long id);
}
