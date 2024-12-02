package ReceiptProcessor.api.dto;

import jakarta.validation.constraints.NotNull;

// Represents an item in the receipt
public class ItemInput {

  @NotNull
  private String name;

  @NotNull
  private Integer quantity;

  @NotNull
  private Double price;

  // Constructor
  public ItemInput(String name, Integer quantity, Double price) {
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }

  // Getters and setters
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
