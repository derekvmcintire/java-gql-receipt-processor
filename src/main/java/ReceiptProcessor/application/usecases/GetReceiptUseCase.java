package ReceiptProcessor.application.usecases;

import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.infrastructure.repository.ReceiptRepository;
import org.springframework.stereotype.Component;

@Component
public class GetReceiptUseCase {

  private final ReceiptRepository receiptRepository;

  public GetReceiptUseCase(ReceiptRepository receiptRepository) {
    this.receiptRepository = receiptRepository;
  }

  public ReceiptResponse execute(String id) {
    return receiptRepository.findById(id);
  }
}
