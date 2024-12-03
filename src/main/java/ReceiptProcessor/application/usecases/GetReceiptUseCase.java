package ReceiptProcessor.application.usecases;

import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.infrastructure.repository.ReceiptMemcachedRepository;
import org.springframework.stereotype.Component;

@Component
public class GetReceiptUseCase {

  private final ReceiptMemcachedRepository receiptMemcachedRepository;

  public GetReceiptUseCase(ReceiptMemcachedRepository receiptMemcachedRepository) {
    this.receiptMemcachedRepository = receiptMemcachedRepository;
  }

  public ReceiptResponse execute(String id) {
    return receiptMemcachedRepository.findInCache(id);
  }
}
