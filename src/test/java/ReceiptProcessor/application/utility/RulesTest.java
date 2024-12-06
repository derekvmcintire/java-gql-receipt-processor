package ReceiptProcessor.application.utility;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import ReceiptProcessor.api.dto.AddItemInput;
import ReceiptProcessor.api.dto.AddReceiptInput;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

class RuleTest {

  // Rule 1: AddPointsForRetailName
  @Test
  void testAddPointsForRetailName() {
    AddPointsForRetailName rule = new AddPointsForRetailName();

    // Test with special characters in store name
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setStore("Store!@#123");
    int points = rule.calculate(receipt);
    assertEquals(8, points); // Only alphanumeric characters should be counted

    // Test with a simple store name
    receipt.setStore("StoreName");
    points = rule.calculate(receipt);
    assertEquals(9, points); // "StoreName" has 9 alphanumeric characters
  }

  // Rule 2: AddPointsForRoundDollarTotal
  @Test
  void testAddPointsForRoundDollarTotal() {
    AddPointsForRoundDollarTotal rule = new AddPointsForRoundDollarTotal();

    // Test with a round dollar total
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setTotal(100.00);
    int points = rule.calculate(receipt);
    assertEquals(50, points); // Should return 50 points for round total

    // Test with a non-round total
    receipt.setTotal(100.25);
    points = rule.calculate(receipt);
    assertEquals(0, points); // Should return 0 points for non-round total
  }

  // Rule 3: AddPointsForMultipleOfQuarter
  @Test
  void testAddPointsForMultipleOfQuarter() {
    AddPointsForMultipleOfQuarter rule = new AddPointsForMultipleOfQuarter();

    // Test with a total that is a multiple of 0.25
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setTotal(10.25);
    int points = rule.calculate(receipt);
    assertEquals(25, points); // Should return 25 points

    // Test with a total that is not a multiple of 0.25
    receipt.setTotal(10.30);
    points = rule.calculate(receipt);
    assertEquals(0, points); // Should return 0 points
  }

  // Rule 4: AddPointsForItemCount
  @Test
  void testAddPointsForItemCount() {
    AddPointsForItemCount rule = new AddPointsForItemCount();

    // Test with 2 items
    AddReceiptInput receipt = new AddReceiptInput();
    AddItemInput item1 = new AddItemInput();
    item1.setName("item1");
    item1.setPrice(5.0);
    item1.setQuantity(1);

    AddItemInput item2 = new AddItemInput();
    item2.setName("item2");
    item2.setPrice(5.0);
    item2.setQuantity(1);

    receipt.setItems(Arrays.asList(item1, item2)); // 2 items
    int points = rule.calculate(receipt);
    assertEquals(5, points); // Should return 5 points for 2 items

    // Test with an odd number of items (3 items)
    AddItemInput item3 = new AddItemInput();
    item3.setName("item3");
    item3.setPrice(5.0);
    item3.setQuantity(1);

    receipt.setItems(Arrays.asList(item1, item2, item3)); // 3 items
    points = rule.calculate(receipt);
    assertEquals(5, points); // 3 items should return 5 points (floor(3/2) * 5 = 5 points)
  }

  // Rule 5: AddPointsForItemDescriptions
  @Test
  void testAddPointsForItemDescriptions() {
    AddPointsForItemDescriptions rule = new AddPointsForItemDescriptions();

    // Test with one item whose name length is multiple of 3
    AddReceiptInput receipt = new AddReceiptInput();
    AddItemInput item = new AddItemInput();
    item.setName("apple");
    item.setPrice(5.0);
    item.setQuantity(1);

    receipt.setItems(Collections.singletonList(item)); // Single item
    int points = rule.calculate(receipt);
    assertEquals(0, points); // "apple" has length 5, not a multiple of 3

    // Test with one item whose name length is a multiple of 3
    item.setName("banana");
    item.setPrice(7.0); // Length of "banana" is 6, a multiple of 3
    receipt.setItems(Collections.singletonList(item));
    points = rule.calculate(receipt);
    assertEquals(14, points); // Price (7.0) * 2 = 14 points
  }

  // Rule 6: AddPointsForOddPurchaseDate
  @Test
  void testAddPointsForOddPurchaseDate() {
    AddPointsForOddPurchaseDate rule = new AddPointsForOddPurchaseDate();

    // Test with a receipt where the day of the month is odd
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setDate(LocalDateTime.of(2024, 12, 5, 14, 0, 0, 0).toString()); // Odd day
    int points = rule.calculate(receipt);
    assertEquals(6, points); // Odd day (5th) should return 6 points

    // Test with a receipt where the day of the month is even
    receipt.setDate(LocalDateTime.of(2024, 12, 6, 14, 0, 0, 0).toString()); // Even day
    points = rule.calculate(receipt);
    assertEquals(0, points); // Even day (6th) should return 0 points
  }

  // Rule 7: AddPointsForPurchaseTime
  @Test
  void testAddPointsForPurchaseTime() {
    AddPointsForPurchaseTime rule = new AddPointsForPurchaseTime();

    // Test with a time in the range 2:00 PM to 4:00 PM
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setDate(LocalDateTime.of(2024, 12, 6, 15, 0, 0, 0).toString()); // Time is 3:00 PM
    int points = rule.calculate(receipt);
    assertEquals(10, points); // Time between 2:00 PM and 4:00 PM should return 10 points

    // Test with a time outside the range
    receipt.setDate(LocalDateTime.of(2024, 12, 6, 17, 0, 0, 0).toString()); // Time is 5:00 PM
    points = rule.calculate(receipt);
    assertEquals(0, points); // Time outside the range should return 0 points
  }
}
