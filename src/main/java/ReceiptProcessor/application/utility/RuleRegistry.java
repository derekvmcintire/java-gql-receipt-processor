package ReceiptProcessor.application.utility;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * RuleRegistry is a Spring-managed component that holds a list of all the Rule
 * implementations.
 * 
 * The rules are automatically injected by Spring when the application starts,
 * using
 * Dependency Injection (DI) to populate the 'rules' list with all beans that
 * implement
 * the 'Rule' interface. This allows you to easily extend or add new rules
 * without modifying
 * this class.
 * 
 * Each Rule implementation is expected to be annotated with @Component, and
 * Spring will
 * automatically discover and register them during the component scanning
 * process.
 * 
 * This class exposes the list of rules to be used in other components, like
 * PointsCalculator.
 */
@Component // Marks this class as a Spring-managed bean to be injected into other
           // components.
public class RuleRegistry {

  // A list of all Rule beans, automatically populated by Spring with any classes
  // annotated with @Component and implementing the Rule interface.
  private final List<Rule> rules;

  /**
   * Constructor for RuleRegistry.
   * 
   * @param rules A list of Rule beans, automatically injected by Spring.
   *              Spring scans the application for any beans that implement
   *              the Rule interface and automatically populates this list.
   */
  public RuleRegistry(List<Rule> rules) {
    this.rules = rules; // Assigns the injected list of rules to the private field.
  }

  /**
   * Returns the list of Rule beans.
   * 
   * @return List of all Rule beans that were injected by Spring.
   */
  public List<Rule> getRules() {
    return rules;
  }
}
