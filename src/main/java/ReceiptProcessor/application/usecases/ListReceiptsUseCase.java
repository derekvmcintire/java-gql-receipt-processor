package ReceiptProcessor.application.usecases;

import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.infrastructure.repository.ReceiptRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListReceiptsUseCase {

  private final ReceiptRepository receiptRepository;

  public ListReceiptsUseCase(ReceiptRepository receiptRepository) {
    this.receiptRepository = receiptRepository;
  }

  public List<ReceiptResponse> execute() {
    return receiptRepository.findAll();
  }
}
