package ReceiptProcessor.application.utility;

import org.springframework.stereotype.Component;

import ReceiptProcessor.api.dto.AddReceiptInput;

/**
 * PointsCalculator is responsible for calculating points based on a given
 * receipt.
 * 
 * It uses a list of rules to determine how many points to assign to a receipt.
 * These rules are retrieved from the RuleRegistry, which holds all Rule beans
 * (i.e., classes that implement the Rule interface and are annotated
 * with @Component).
 * 
 * Spring will automatically inject the RuleRegistry bean, which contains all
 * the rules,
 * so new rules can be added or modified without needing to change this class.
 */
@Component // Marks this class as a Spring-managed component for dependency injection.
public class PointsCalculator {

  // The RuleRegistry is injected automatically by Spring. It provides access to
  // all Rule beans.
  private final RuleRegistry ruleRegistry;

  /**
   * Constructor for PointsCalculator.
   * 
   * @param ruleRegistry An instance of RuleRegistry, automatically injected by
   *                     Spring.
   *                     It holds a list of all the Rule implementations.
   */
  public PointsCalculator(RuleRegistry ruleRegistry) {
    this.ruleRegistry = ruleRegistry; // Assign the injected RuleRegistry to the private field.
  }

  /**
   * Calculates the total points for the given receipt by applying all registered
   * rules.
   * 
   * This method iterates through all rules in the RuleRegistry and applies each
   * one
   * to the provided receipt. The results from all rules are summed to get the
   * total points.
   * 
   * @param receipt The receipt for which the points are calculated.
   * @return The total points calculated based on the rules.
   */
  public int calculatePoints(AddReceiptInput receipt) {
    return ruleRegistry.getRules() // Retrieves the list of rules from RuleRegistry.
        .stream() // Converts the list to a Stream to apply operations on each rule.
        .mapToInt(rule -> rule.calculate(receipt)) // For each rule, apply the calculate method and get the result
        .sum(); // Sums up the results of all rules to get the total points.
  }
}
