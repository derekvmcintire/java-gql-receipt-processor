package ReceiptProcessor.api.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class AddReceiptInput {

  @NotNull
  private String store;

  @NotNull
  private String date;

  @NotNull
  private Double total;

  @NotNull
  private List<@Valid ItemInput> items;

  // Constructor
  public AddReceiptInput(String store, String date, Double total, List<ItemInput> items) {
    this.store = store;
    this.date = date;
    this.total = total;
    this.items = items;
  }

  // Getters and setters
  public String getStore() {
    return store;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Double getTotal() {
    return total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public List<ItemInput> getItems() {
    return items;
  }

  public void setItems(List<ItemInput> items) {
    this.items = items;
  }
}
