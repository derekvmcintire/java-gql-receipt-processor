package ReceiptProcessor.domain.model;

public class Item {
  private String name;
  private Integer quantity;
  private Double price;

  // Constructor with parameters
  public Item(String name, Integer quantity, Double price) {
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
