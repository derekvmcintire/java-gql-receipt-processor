package ReceiptProcessor.api.dto;

// Represents an item in the receipt
public class ItemInput {

  private String name; // Name of the item (e.g., "Milk")
  private Integer quantity; // Quantity of the item (e.g., 2)
  private Double price; // Price per unit of the item (e.g., 2.5)

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
