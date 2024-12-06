package ReceiptProcessor.application.utility;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ReceiptProcessor.api.dto.AddReceiptInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Arrays;

class PointsCalculatorTest {

  private PointsCalculator pointsCalculator;
  private RuleRegistry mockRuleRegistry;

  private AddReceiptInput receipt;

  @BeforeEach
  void setUp() {
    // Initialize Mockito annotations
    MockitoAnnotations.openMocks(this);

    // Create a mock RuleRegistry
    mockRuleRegistry = mock(RuleRegistry.class);

    // Initialize PointsCalculator with the mock RuleRegistry
    pointsCalculator = new PointsCalculator(mockRuleRegistry);

    // Create the AddReceiptInput object with necessary fields
    receipt = new AddReceiptInput(); // Initialize as needed for the test
  }

  @Test
  void testCalculatePoints() {
    // Create specific Rule mocks
    Rule mockRule1 = mock(Rule.class);
    Rule mockRule2 = mock(Rule.class);
    Rule mockRule3 = mock(Rule.class);

    // Configure the mocked rules list to return specific rules
    List<Rule> rules = Arrays.asList(mockRule1, mockRule2, mockRule3);

    // Configure the mock RuleRegistry to return the list of rules
    when(mockRuleRegistry.getRules()).thenReturn(rules);

    // Set up the behavior for the individual rules
    when(mockRule1.calculate(receipt)).thenReturn(10);
    when(mockRule2.calculate(receipt)).thenReturn(50);
    when(mockRule3.calculate(receipt)).thenReturn(25);

    // Act: Call the method under test
    int totalPoints = pointsCalculator.calculatePoints(receipt);

    // Assert: Verify the total points calculation
    int expectedPoints = 10 + 50 + 25; // Sum of mocked return values
    assertEquals(expectedPoints, totalPoints);

    // Verify that the calculate method was called for each rule
    verify(mockRule1).calculate(receipt);
    verify(mockRule2).calculate(receipt);
    verify(mockRule3).calculate(receipt);
  }
}
