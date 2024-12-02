package ReceiptProcessor.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReceiptResponse {

  private String id; // The unique identifier for the receipt (ID in GraphQL)
  private Double total; // The total amount of the receipt (Float in GraphQL)
  private List<ItemResponse> items; // List of items in the receipt (ItemResponse in GraphQL)

  // Constructor
  public ReceiptResponse(String id, Double total, List<ItemResponse> items) {
    this.id = id;
    this.total = total;
    this.items = items;
  }
}
