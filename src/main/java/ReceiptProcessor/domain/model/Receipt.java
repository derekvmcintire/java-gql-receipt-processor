package ReceiptProcessor.domain.model;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
public class Receipt {

  @NotEmpty
  private String id;

  @NotEmpty
  private String store;

  @NotEmpty
  private String date;

  @NotNull
  private List<Item> items;

  @NotNull
  private Double total;
}
