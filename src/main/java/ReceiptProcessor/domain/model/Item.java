package ReceiptProcessor.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Item {
  @NotNull
  private String name;

  @NotNull
  private Integer quantity;

  @NotNull
  private Double price;
}
