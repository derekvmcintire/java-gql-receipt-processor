package ReceiptProcessor.application.utility;

import ReceiptProcessor.api.dto.AddItemInput;
import ReceiptProcessor.api.dto.AddReceiptInput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointsCalculatorRulesTest {

  private PointsCalculatorRules pointsCalculatorRules;

  @BeforeEach
  void setUp() {
    pointsCalculatorRules = new PointsCalculatorRules();
  }

  @Test
  void testAddPointsForRetailName() {
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setStore("Store123!@#");
    int points = pointsCalculatorRules.addPointsForRetailName(receipt);
    assertEquals(8, points); // "Store123" has 8 alphanumeric characters
  }

  @Test
  void testAddPointsForRoundDollarTotal() {
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setTotal(100.0); // Round dollar total
    int points = pointsCalculatorRules.addPointsForRoundDollarTotal(receipt);
    assertEquals(50, points);

    receipt.setTotal(100.25); // Not a round dollar total
    points = pointsCalculatorRules.addPointsForRoundDollarTotal(receipt);
    assertEquals(0, points);
  }

  @Test
  void testAddPointsForMultipleOfQuarter() {
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setTotal(100.25); // Multiple of 0.25
    int points = pointsCalculatorRules.addPointsForMultipleOfQuarter(receipt);
    assertEquals(25, points);

    receipt.setTotal(100.30); // Not a multiple of 0.25
    points = pointsCalculatorRules.addPointsForMultipleOfQuarter(receipt);
    assertEquals(0, points);
  }

  @Test
  void testAddPointsForItemCount() {
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setItems(List.of(new AddItemInput(), new AddItemInput(), new AddItemInput()));
    int points = pointsCalculatorRules.addPointsForItemCount(receipt);
    assertEquals(5, points); // 3 items = 5 points (floor(3 / 2) * 5)
  }

  @Test
  void testAddPointsForItemDescriptions() {
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setItems(List.of(
        new AddItemInput("Banana", 1, 2.0), // Length 6 (multiple of 3), price 2.0
        new AddItemInput("Apple", 1, 3.0) // Length 5 (not a multiple of 3)
    ));
    int points = pointsCalculatorRules.addPointsForItemDescriptions(receipt);
    assertEquals(4, points); // Only "Banana" qualifies: ceil(2.0 * 2) = 4
  }

  @Test
  void testAddPointsForOddPurchaseDate() {
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setDate("2023-11-01T10:00:00"); // November 1st (odd day)
    int points = pointsCalculatorRules.addPointsForOddPurchaseDate(receipt);
    assertEquals(6, points);

    receipt.setDate("2023-11-02T10:00:00"); // November 2nd (even day)
    points = pointsCalculatorRules.addPointsForOddPurchaseDate(receipt);
    assertEquals(0, points);
  }

  @Test
  void testAddPointsForPurchaseTime() {
    AddReceiptInput receipt = new AddReceiptInput();
    receipt.setDate("2023-11-01T14:30:00"); // Within 2:00 PM - 4:00 PM
    int points = pointsCalculatorRules.addPointsForPurchaseTime(receipt);
    assertEquals(10, points);

    receipt.setDate("2023-11-01T13:59:00"); // Outside the range
    points = pointsCalculatorRules.addPointsForPurchaseTime(receipt);
    assertEquals(0, points);
  }
}
