package ReceiptProcessor.api.dto;

import lombok.Data;

@Data
public class ItemResponse {

  private String name; // Name of the item
  private Integer quantity; // Quantity of the item
  private Double price; // Price per unit of the item

  // Constructor
  public ItemResponse(String name, Integer quantity, Double price) {
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }
}
