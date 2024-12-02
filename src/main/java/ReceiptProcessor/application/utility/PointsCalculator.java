package ReceiptProcessor.application.utility;

import org.springframework.stereotype.Component;

import ReceiptProcessor.api.dto.AddReceiptInput;

@Component
public class PointsCalculator {
  private final PointsCalculatorRules rules;

  public PointsCalculator(
      PointsCalculatorRules pointsCalculatorRules) {
    this.rules = pointsCalculatorRules;
  }

  public int calculatePoints(AddReceiptInput receipt) {
    return rules.addPointsForRetailName(receipt)
        + rules.addPointsForRoundDollarTotal(receipt)
        + rules.addPointsForMultipleOfQuarter(receipt)
        + rules.addPointsForItemCount(receipt)
        + rules.addPointsForItemDescriptions(receipt)
        + rules.addPointsForOddPurchaseDate(receipt)
        + rules.addPointsForPurchaseTime(receipt);
  }
}
