package com.mercadona.kata.hexagonal.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Batch {
    Long batchId;
    Store storeId;
    Product productId;
    LocalDate entryDate;
    LocalDate expirationDate;
    boolean isDiscounted;
    LocalDate discountDate;
    boolean removed;
}
