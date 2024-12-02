package ReceiptProcessor.application.usecases;

import ReceiptProcessor.domain.model.Receipt;
import ReceiptProcessor.infrastructure.repository.ReceiptRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddReceiptUseCase {

  private final ReceiptRepository receiptRepository;

  public AddReceiptUseCase(ReceiptRepository receiptRepository) {
    this.receiptRepository = receiptRepository;
  }

  public Receipt execute(Receipt receipt) {
    // Step 1: Validate the receipt using the business logic
    validateReceipt(receipt);

    // Step 2: Check if ID is provided, if not, generate a new ID
    if (receipt.getId() == null || receipt.getId().isEmpty()) {
      receipt.setId(UUID.randomUUID().toString()); // Generate a unique ID using UUID
    }

    // Step 3: Save the validated receipt to the repository
    return receiptRepository.save(receipt);
  }

  private void validateReceipt(Receipt receipt) {
    double computedTotal = receipt.getItems().stream()
        .mapToDouble(item -> item.getPrice() * item.getQuantity())
        .sum();

    if (computedTotal != receipt.getTotal()) {
      throw new IllegalArgumentException("Total does not match item prices.");
    }
  }
}
