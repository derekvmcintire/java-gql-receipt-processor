package ReceiptProcessor.application.utility;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ReceiptProcessor.api.dto.AddReceiptInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointsCalculatorTest {

  private PointsCalculator pointsCalculator;
  private PointsCalculatorRules mockRules;
  private AddReceiptInput receipt;

  @BeforeEach
  void setUp() {
    mockRules = mock(PointsCalculatorRules.class);
    pointsCalculator = new PointsCalculator(mockRules);
    receipt = new AddReceiptInput(); // Initialize your AddReceiptInput object with necessary fields.
  }

  @Test
  void testCalculatePoints() {
    // Arrange: Set up mock behavior for each rule method.
    when(mockRules.addPointsForRetailName(receipt)).thenReturn(10);
    when(mockRules.addPointsForRoundDollarTotal(receipt)).thenReturn(50);
    when(mockRules.addPointsForMultipleOfQuarter(receipt)).thenReturn(25);
    when(mockRules.addPointsForItemCount(receipt)).thenReturn(15);
    when(mockRules.addPointsForItemDescriptions(receipt)).thenReturn(30);
    when(mockRules.addPointsForOddPurchaseDate(receipt)).thenReturn(6);
    when(mockRules.addPointsForPurchaseTime(receipt)).thenReturn(10);

    // Act: Call the method under test.
    int totalPoints = pointsCalculator.calculatePoints(receipt);

    // Assert: Verify the total points calculation.
    int expectedPoints = 10 + 50 + 25 + 15 + 30 + 6 + 10; // Sum of mocked return values
    assertEquals(expectedPoints, totalPoints);

    // Optionally verify that the methods were called exactly once.
    verify(mockRules).addPointsForRetailName(receipt);
    verify(mockRules).addPointsForRoundDollarTotal(receipt);
    verify(mockRules).addPointsForMultipleOfQuarter(receipt);
    verify(mockRules).addPointsForItemCount(receipt);
    verify(mockRules).addPointsForItemDescriptions(receipt);
    verify(mockRules).addPointsForOddPurchaseDate(receipt);
    verify(mockRules).addPointsForPurchaseTime(receipt);
  }
}
