package ReceiptProcessor.application.usecases;

import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.infrastructure.repository.ReceiptRepository;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddReceiptUseCase {

  private final ReceiptRepository receiptRepository;

  // @Autowired
  public AddReceiptUseCase(ReceiptRepository receiptRepository) {
    this.receiptRepository = receiptRepository;
  }

  public ReceiptResponse execute(AddReceiptInput input) {
    return receiptRepository.save(input);
  }
}
