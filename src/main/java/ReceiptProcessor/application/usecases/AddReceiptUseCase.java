package ReceiptProcessor.application.usecases;

import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.api.dto.ItemResponse;
import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.application.utility.PointsCalculator;
import ReceiptProcessor.infrastructure.repository.ReceiptMemcachedRepository;
import ReceiptProcessor.infrastructure.repository.ReceiptRepository;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class AddReceiptUseCase {

  private final ReceiptMemcachedRepository receiptMemcachedRepository;
  private final ReceiptRepository receiptRepository;
  private final PointsCalculator pointsCalculator;

  public AddReceiptUseCase(ReceiptMemcachedRepository receiptMemcachedRepository, ReceiptRepository receiptRepository,
      PointsCalculator pointsCalculator) {
    this.receiptMemcachedRepository = receiptMemcachedRepository;
    this.receiptRepository = receiptRepository;
    this.pointsCalculator = pointsCalculator;
  }

  public ReceiptResponse execute(AddReceiptInput input) {
    // Calculate points
    int calculatedPoints = pointsCalculator.calculatePoints(input);

    // Map AddReceiptInput to ReceiptResponse (including points)
    ReceiptResponse receipt = new ReceiptResponse();
    receipt.setId(UUID.randomUUID().toString());
    receipt.setStore(input.getStore());
    receipt.setDate(input.getDate());
    receipt.setTotal(input.getTotal());
    receipt.setPoints(calculatedPoints); // Set points
    receipt.setItems(input.getItems().stream().map(item -> {
      ItemResponse itemResponse = new ItemResponse();
      itemResponse.setId(UUID.randomUUID().toString());
      itemResponse.setName(item.getName());
      itemResponse.setQuantity(item.getQuantity());
      itemResponse.setPrice(item.getPrice());
      return itemResponse;
    }).toList());

    // Save the receipt
    ReceiptResponse result = receiptRepository.save(receipt);

    // Save points in memcache
    receiptMemcachedRepository.saveToCache(receipt);

    return result;
  }
}
