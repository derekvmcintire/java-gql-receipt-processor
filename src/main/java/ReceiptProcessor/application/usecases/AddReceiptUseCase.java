package ReceiptProcessor.application.usecases;

import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.api.dto.ItemResponse;
import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.application.utility.PointsCalculator;
import ReceiptProcessor.infrastructure.repository.ReceiptRepository;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class AddReceiptUseCase {

  private final ReceiptRepository receiptRepository;
  private final PointsCalculator pointsCalculator;

  public AddReceiptUseCase(ReceiptRepository receiptRepository, PointsCalculator pointsCalculator) {
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

    System.out.println("*************Saving Receipt with points: " + receipt.getPoints());

    // Save the receipt
    ReceiptResponse result = receiptRepository.save(receipt);
    System.out.println("*************Returning Receipt with points: " + result.getPoints());

    return result;
  }
}
