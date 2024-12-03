package ReceiptProcessor.application.usecases;

import ReceiptProcessor.api.dto.ReceiptPointsResponse;
import ReceiptProcessor.infrastructure.repository.ReceiptMemcachedRepository;
import org.springframework.stereotype.Component;

@Component
public class GetReceiptPointsUseCase {

  private final ReceiptMemcachedRepository receiptMemcachedRepository;

  public GetReceiptPointsUseCase(ReceiptMemcachedRepository receiptMemcachedRepository) {
    this.receiptMemcachedRepository = receiptMemcachedRepository;
  }

  public ReceiptPointsResponse execute(String id) {
    ReceiptPointsResponse pointsResponse = new ReceiptPointsResponse();
    pointsResponse.setId(id);
    pointsResponse.setPoints(receiptMemcachedRepository.findInCache(id));
    return pointsResponse;
  }
}
