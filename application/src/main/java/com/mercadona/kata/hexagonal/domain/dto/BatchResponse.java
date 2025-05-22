package com.mercadona.kata.hexagonal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchResponse {
    private Long batchId;
    private Long storeId;
    private Long productId;
    private LocalDate entryDate;
    private LocalDate expirationDate;
    private boolean isDiscounted;
    private LocalDate discountDate;
    private boolean removed;
}
