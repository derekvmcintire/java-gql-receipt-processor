package ReceiptProcessor.api.dto;

import java.util.Objects;

public class ItemResponse {
  private String id;
  private String name;
  private int quantity;
  private double price;

  // Default constructor
  public ItemResponse() {
  }

  // Parameterized constructor
  public ItemResponse(String id, String name, int quantity, double price) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }

  // Getters
  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getQuantity() {
    return quantity;
  }

  public double getPrice() {
    return price;
  }

  // Setters
  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  // toString method for debugging
  @Override
  public String toString() {
    return "ItemResponse{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", quantity=" + quantity +
        ", price=" + price +
        '}';
  }

  // equals and hashCode for object comparison (optional but recommended)
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ItemResponse that = (ItemResponse) o;
    return quantity == that.quantity &&
        Double.compare(that.price, price) == 0 &&
        Objects.equals(id, that.id) &&
        Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, quantity, price);
  }
}
