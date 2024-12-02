package ReceiptProcessor.application.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import ReceiptProcessor.api.dto.AddItemInput;
import ReceiptProcessor.api.dto.AddReceiptInput;

// Helper functions for calculating points based on specific business rules:

@Component
public class PointsCalculatorRules {
  // Rule 1: One point for every alphanumeric character in the retailer name.
  public int addPointsForRetailName(AddReceiptInput receipt) {
    String alphaNumericalRetailName = receipt.getStore().replaceAll("[^A-Za-z0-9]", "");
    return alphaNumericalRetailName.length();
  }

  // Rule 2: 50 points if the total is a round dollar amount (no cents).
  public int addPointsForRoundDollarTotal(AddReceiptInput receipt) {
    if (receipt.getTotal() % 1 == 0) {
      return 50;
    }
    return 0;
  }

  // Rule 3: 25 points if the total is a multiple of 0.25.
  public int addPointsForMultipleOfQuarter(AddReceiptInput receipt) {
    if (receipt.getTotal() % .25 == 0) {
      return 25;
    }
    return 0;
  }

  // Rule 4: 5 points for every two items on the receipt.
  public int addPointsForItemCount(AddReceiptInput receipt) {
    int x = (int) Math.floor(receipt.getItems().size() / 2);
    return x * 5;
  }

  // Rule 5: If the length of the trimmed item description is a multiple of 3,
  // multiply the item price by 0.2 and round up to the nearest integer to
  // determine the points for that item.
  public int addPointsForItemDescriptions(AddReceiptInput receipt) {
    int points = 0;

    for (AddItemInput item : receipt.getItems()) {
      if (item.getName().trim().length() % 3 == 0) {
        points += (int) Math.round(item.getPrice() * 2);
      }
    }

    return points;
  }

  // Rule 6: 6 points if the day in the purchase date is odd (e.g., 1st, 3rd, 5th,
  // etc.).
  public int addPointsForOddPurchaseDate(AddReceiptInput receipt) {
    LocalDateTime date = LocalDateTime.parse(receipt.getDate());
    int day = date.getDayOfMonth();
    if (day % 2 == 0) {
      return 6;
    }
    return 0;
  }

  // Rule 7: 10 points if the purchase time is between 2:00 PM and 4:00 PM
  // (inclusive).
  public int addPointsForPurchaseTime(AddReceiptInput receipt) {
    LocalDateTime date = LocalDateTime.parse(receipt.getDate());
    int hour = date.getHour();
    if (hour >= 14 && hour <= 16) {
      return 10;
    }
    return 0;
  }
}
