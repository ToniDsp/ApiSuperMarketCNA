package com.mercadona.kata.hexagonal.application.exceptions;

import lombok.Getter;

@Getter
public class ProductStoreBatchExceptions extends RuntimeException {
  private final ErrorCode errorCode;

  public ProductStoreBatchExceptions(ErrorCode errorCode){
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
