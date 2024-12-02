package ReceiptProcessor.application;

import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.infrastructure.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptService {

  @Autowired
  private ReceiptRepository receiptRepository;

  public ReceiptResponse getReceipt(String id) {
    return receiptRepository.findById(id);
  }

  public List<ReceiptResponse> listReceipts() {
    return receiptRepository.findAll();
  }

  public ReceiptResponse addReceipt(AddReceiptInput input) {
    return receiptRepository.save(input);
  }
}
