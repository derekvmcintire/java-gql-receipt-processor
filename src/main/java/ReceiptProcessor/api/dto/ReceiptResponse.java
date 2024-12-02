package ReceiptProcessor.api.dto;

import java.util.List;
import java.util.Objects;

public class ReceiptResponse {
  private String id;
  private String store;
  private String date;
  private double total;
  private List<ItemResponse> items;

  // Default constructor
  public ReceiptResponse() {
  }

  // Parameterized constructor
  public ReceiptResponse(String id, String store, String date, double total, List<ItemResponse> items) {
    this.id = id;
    this.store = store;
    this.date = date;
    this.total = total;
    this.items = items;
  }

  // Getters
  public String getId() {
    return id;
  }

  public String getStore() {
    return store;
  }

  public String getDate() {
    return date;
  }

  public double getTotal() {
    return total;
  }

  public List<ItemResponse> getItems() {
    return items;
  }

  // Setters
  public void setId(String id) {
    this.id = id;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public void setItems(List<ItemResponse> items) {
    this.items = items;
  }

  // toString method for debugging
  @Override
  public String toString() {
    return "ReceiptResponse{" +
        "id='" + id + '\'' +
        ", store='" + store + '\'' +
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
    ReceiptResponse that = (ReceiptResponse) o;
    return Double.compare(that.total, total) == 0 &&
        Objects.equals(id, that.id) &&
        Objects.equals(store, that.store) &&
        Objects.equals(date, that.date) &&
        Objects.equals(items, that.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, store, date, total, items);
  }
}
