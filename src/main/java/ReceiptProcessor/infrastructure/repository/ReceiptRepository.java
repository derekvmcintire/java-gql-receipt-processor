package ReceiptProcessor.infrastructure.repository;

import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.api.dto.ItemResponse;
import ReceiptProcessor.api.dto.ReceiptResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ReceiptRepository {
  private final List<ReceiptResponse> database = new ArrayList<>();

  public ReceiptResponse findById(String id) {
    return database.stream()
        .filter(receipt -> receipt.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  public List<ReceiptResponse> findAll() {
    return database;
  }

  public ReceiptResponse save(ReceiptResponse receipt) {
    database.add(receipt);
    return receipt;
  }
}
