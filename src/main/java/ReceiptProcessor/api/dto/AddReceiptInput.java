package ReceiptProcessor.api.dto;

import java.util.List;
import java.util.Objects;

public class AddReceiptInput {
  private String store;
  private String date;
  private double total;
  private List<AddItemInput> items;

  // Default constructor
  public AddReceiptInput() {
  }

  // Parameterized constructor
  public AddReceiptInput(String store, String date, double total, List<AddItemInput> items) {
    this.store = store;
    this.date = date;
    this.total = total;
    this.items = items;
  }

  // Getters
  public String getStore() {
    return store;
  }

  public String getDate() {
    return date;
  }

  public double getTotal() {
    return total;
  }

  public List<AddItemInput> getItems() {
    return items;
  }

  // Setters
  public void setStore(String store) {
    this.store = store;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public void setItems(List<AddItemInput> items) {
    this.items = items;
  }

  // toString method for debugging
  @Override
  public String toString() {
    return "AddReceiptInput{" +
        "store='" + store + '\'' +
        ", date='" + date + '\'' +
        ", total=" + total +
        ", items=" + items +
        '}';
  }

  // equals and hashCode for object comparison (optional but recommended)
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    AddReceiptInput that = (AddReceiptInput) o;
    return Double.compare(that.total, total) == 0 &&
        Objects.equals(store, that.store) &&
        Objects.equals(date, that.date) &&
        Objects.equals(items, that.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(store, date, total, items);
  }
}
