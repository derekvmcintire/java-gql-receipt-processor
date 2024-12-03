package ReceiptProcessor.application.usecases;

import ReceiptProcessor.infrastructure.repository.ReceiptMemcachedRepository;
import org.springframework.stereotype.Component;

@Component
public class GetReceiptPointsUseCase {

  private final ReceiptMemcachedRepository receiptMemcachedRepository;

  public GetReceiptPointsUseCase(ReceiptMemcachedRepository receiptMemcachedRepository) {
    this.receiptMemcachedRepository = receiptMemcachedRepository;
  }

  public int execute(String id) {
    return receiptMemcachedRepository.findInCache(id);
  }
}
