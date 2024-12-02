package ReceiptProcessor.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReceiptResponse {

  private String id; // The unique identifier for the receipt (ID in GraphQL)
  private String store; // The store name (String in GraphQL)
  private String date; // The date of the receipt (String in GraphQL)
  private Double total; // The total amount of the receipt (Float in GraphQL)
  private List<ItemResponse> items; // List of items in the receipt (ItemResponse in GraphQL)

  // Constructor
  public ReceiptResponse(String id, String store, String date, Double total, List<ItemResponse> items) {
    this.id = id;
    this.store = store;
    this.date = date;
    this.total = total;
    this.items = items;
  }
}
