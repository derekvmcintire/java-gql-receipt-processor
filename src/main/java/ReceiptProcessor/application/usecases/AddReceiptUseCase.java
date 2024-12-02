package ReceiptProcessor.application.usecases;

import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.application.utility.PointsCalculator;
import ReceiptProcessor.infrastructure.repository.ReceiptRepository;
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
    input.setPoints(pointsCalculator.calculatePoints(input));
    return receiptRepository.save(input);
  }
}
