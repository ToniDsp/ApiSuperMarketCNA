package com.mercadona.kata.hexagonal.driven.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "batch_product_store", uniqueConstraints = {
  @UniqueConstraint(columnNames = {"product_id", "store_id", "entry_date"})
})
public class BatchMO extends Auditable{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "batch_id")
  private long batchId;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  @JsonProperty("productId")
  private ProductMO product;

  @ManyToOne
  @JoinColumn(name = "store_id", nullable = false)
  @JsonProperty("storeId")
  private StoreMO store;

  @Column(name = "entry_date", nullable = false)
  private LocalDate entryDate;

  @Column(name = "expiration_date", nullable = false)
  private LocalDate expirationDate;

  @Column(name = "is_discounted", nullable = false)
  private boolean isDiscounted = false;

  @Column(name = "discount_date")
  private LocalDate discountDate;

  @Column(name = "removed", nullable = false)
  private boolean removed = false;
}
