package com.mercadona.kata.hexagonal.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  ERROR_PRODUCT_DONT_EXIST("KBK01", "The product doesn't find"),
  ERROR_PRODUCT_NAME("KBK02", "The product name is obligatory"),
  ERROR_PRODUCT_SAVE("KBK03", "Error to save a product"),
  ERROR_PRODUCT_UPDATE("KBK04", "Error to update a product"),
  ERROR_PRODUCT_DELETE("KBK05", "Error to delete a product"),
  ERROR_PRODUCT_LIST_EMPTY("KBK06", "The list of product is empty"),
  ERROR_STORE_DONT_EXIST("KBK07", "The store doesn't find"),
  ERROR_STORE_NAME("KBK08", "Problem with the store name"),
  ERROR_STORE_SAVE("KBK09", "Error to save a store"),
  ERROR_STORE_UPDATE("KBK010", "Error to update a store"),
  ERROR_STORE_DELETE("KBK011", "Error to delete a store"),
  ERROR_STORE_LIST_EMPTY("KBK012", "The list of store is empty"),
  ERROR_BATCH_NULL("KBK013", "Batch is null"),
  ERROR_BATCH_DONT_EXIST("KBK014", "The batch doesn't find"),
  ERROR_BATCH_SAVE("KBK015", "Error to save a batch"),
  ERROR_BATCH_UPDATE("KBK016", "Error to update a batch"),
  ERROR_BATCH_DELETE("KBK017", "Error to delete a batch"),
  ERROR_BATCH_LIST_EMPTY("KBK018", "The list of batch is empty"),
  ERROR_BATCHES_NOT_FOUND_FOR_STORE("KBK019", "The batches is not found for store");

  private final String code;
  private final String message;

}
