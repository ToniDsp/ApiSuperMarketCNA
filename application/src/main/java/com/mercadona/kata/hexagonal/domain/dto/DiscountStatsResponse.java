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
public class DiscountStatsResponse {
    private long totalDiscountedBatches;
    private long uniqueDiscountedProducts;
    private LocalDate lastDiscountDate;
}
