package ReceiptProcessor.application.utility;

import org.springframework.stereotype.Component;

import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.domain.model.Receipt;

// Helper functions for calculating points based on specific business rules:
//   1. One point for every alphanumeric character in the retailer name.
//   2. 50 points if the total is a round dollar amount (no cents).
//   3. 25 points if the total is a multiple of 0.25.
//   4. 5 points for every two items on the receipt.
//   5. If the length of the trimmed item description is a multiple of 3,
//     multiply the item price by 0.2 and round up to the nearest integer to determine the points for that item.
//   6. 6 points if the day in the purchase date is odd (e.g., 1st, 3rd, 5th, etc.).
//   7. 10 points if the purchase time is between 2:00 PM and 4:00 PM (inclusive).

@Component
public class PointsCalculatorRules {
  public int addPointsForRetailName(AddReceiptInput receipt) {
    String alphaNumericalRetailName = receipt.getStore().replaceAll("[^A-Za-z0-9]", "");
    return alphaNumericalRetailName.length();
  }
}
