package ReceiptProcessor.domain.model;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class Receipt {

  @NotNull
  private String id; // Receipt ID

  @NotNull
  private String store; // Store name

  @NotNull
  private String date; // Date of receipt

  @NotNull
  private List<Item> items; // List of items in the receipt

  @NotNull
  private Double total; // Total amount of the receipt
}
