package ReceiptProcessor.application.utility;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import ReceiptProcessor.api.dto.AddItemInput;
import ReceiptProcessor.api.dto.AddReceiptInput;

/**
 * Rule interface defines the contract for all rules that calculate points
 * based on the provided receipt.
 */
interface Rule {
  /**
   * This method calculates points based on the provided receipt.
   * 
   * @param receipt The receipt on which points are calculated.
   * @return The calculated points based on the rule logic.
   */
  int calculate(AddReceiptInput receipt);
}

// Rule 1: One point for every alphanumeric character in the retailer name.
// This rule is applied to the retailer's name and counts the alphanumeric
// characters.
// It is annotated as @Component so Spring can automatically inject it into the
// RuleRegistry.
@Component
class AddPointsForRetailName implements Rule {
  @Override
  public int calculate(AddReceiptInput receipt) {
    // Remove all non-alphanumeric characters and return the length of the resulting
    // string
    String alphaNumericalRetailName = receipt.getStore().replaceAll("[^A-Za-z0-9]", "");
    return alphaNumericalRetailName.length();
  }
}

// Rule 2: 25 points if the total is a multiple of 0.25.
// This rule checks if the receipt total is a multiple of 0.25. If so, it awards
// 50 points.
// Annotated with @Component to register it with Spring's DI container.
@Component
class AddPointsForRoundDollarTotal implements Rule {
  @Override
  public int calculate(AddReceiptInput receipt) {
    // If the total is a whole number (i.e., no decimal points), award 50 points
    return receipt.getTotal() % 1 == 0 ? 50 : 0;
  }
}

// Rule 3: 5 points for every two items on the receipt.
// This rule awards points based on the number of items on the receipt.
@Component
class AddPointsForMultipleOfQuarter implements Rule {
  @Override
  public int calculate(AddReceiptInput receipt) {
    // Award 25 points if the total is a multiple of 0.25
    return receipt.getTotal() % .25 == 0 ? 25 : 0;
  }
}

// Rule 4: If the length of the trimmed item description is a multiple of 3,
// multiply the item price by 0.2 and round up to the nearest integer to
// determine
// the points for that item.
@Component
class AddPointsForItemCount implements Rule {
  @Override
  public int calculate(AddReceiptInput receipt) {
    // Calculate points based on item count; the exact logic depends on how items
    // are structured.
    // For this example, it awards points for every two items on the receipt.
    int x = (int) Math.floor(receipt.getItems().size() / 2);
    return x * 5;
  }
}

// Rule 5: 6 points if the day in the purchase date is odd (e.g., 1st, 3rd, 5th,
// etc.).
// This rule checks the day of the month from the purchase date and awards
// points if it's odd.
@Component
class AddPointsForItemDescriptions implements Rule {
  @Override
  public int calculate(AddReceiptInput receipt) {
    int points = 0;

    // Loop through each item and apply the rule if the description length is a
    // multiple of 3
    for (AddItemInput item : receipt.getItems()) {
      if (item.getName().trim().length() % 3 == 0) {
        // Multiply the price by 2 and round the result
        points += (int) Math.round(item.getPrice() * 2);
      }
    }

    return points;
  }
}

// Rule 6: 10 points if the purchase time is between 2:00 PM and 4:00 PM
// (inclusive).
// This rule checks the purchase time and awards points if the time falls within
// the specified range.
@Component
class AddPointsForOddPurchaseDate implements Rule {
  @Override
  public int calculate(AddReceiptInput receipt) {
    // Check if the day of the month is odd (1st, 3rd, etc.)
    if (LocalDateTime.parse(receipt.getDate()).getDayOfMonth() % 2 != 0) {
      return 6; // Return 6 points if the day is odd
    }
    return 0; // No points if the day is not odd
  }
}

// Rule 7: 10 points if the purchase time is between 2:00 PM and 4:00 PM
// (inclusive).
// This rule checks if the time of the purchase is within a specific range and
// awards points.
@Component
class AddPointsForPurchaseTime implements Rule {
  @Override
  public int calculate(AddReceiptInput receipt) {
    // Check if the hour of the purchase time is between 14:00 (2:00 PM) and 16:00
    // (4:00 PM)
    int hour = LocalDateTime.parse(receipt.getDate()).getHour();
    if (hour >= 14 && hour <= 16) {
      return 10; // Award 10 points for purchases made between 2:00 PM and 4:00 PM
    }
    return 0; // No points outside of this time range
  }
}
