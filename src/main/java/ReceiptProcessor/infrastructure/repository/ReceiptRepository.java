package ReceiptProcessor.infrastructure.repository;

import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.api.dto.ItemResponse;
import ReceiptProcessor.api.dto.ReceiptResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReceiptRepository {
  private final List<ReceiptResponse> database = new ArrayList<>();
  private int idCounter = 1;

  public ReceiptResponse findById(String id) {
    return database.stream()
        .filter(receipt -> receipt.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  public List<ReceiptResponse> findAll() {
    return database;
  }

  public ReceiptResponse save(AddReceiptInput input) {
    ReceiptResponse receipt = new ReceiptResponse();
    receipt.setId(String.valueOf(idCounter++));
    receipt.setStore(input.getStore());
    receipt.setDate(input.getDate());
    receipt.setTotal(input.getTotal());
    receipt.setItems(input.getItems().stream().map(item -> {
      ItemResponse itemResponse = new ItemResponse();
      itemResponse.setId(String.valueOf(idCounter++));
      itemResponse.setName(item.getName());
      itemResponse.setQuantity(item.getQuantity());
      itemResponse.setPrice(item.getPrice());
      return itemResponse;
    }).toList());
    database.add(receipt);
    return receipt;
  }
}
