package ReceiptProcessor.infrastructure.repository;

// Importing the ReceiptResponse DTO and annotations for Spring's Repository component
import ReceiptProcessor.api.dto.ReceiptResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// Marks this class as a Spring-managed repository component, 
// enabling it to be injected as a dependency where needed.
@Repository
public class ReceiptRepository {

  // Simulates a database using an in-memory list to store ReceiptResponse
  // objects.
  private final List<ReceiptResponse> database = new ArrayList<>();

  /**
   * Finds a receipt in the database by its unique ID.
   * 
   * @param id The ID of the receipt to find.
   * @return The matching ReceiptResponse if found; otherwise, null.
   */
  public ReceiptResponse findById(String id) {
    // Streams through the list to locate a receipt with the given ID.
    // Returns the first match or null if no match is found.
    return database.stream()
        .filter(receipt -> receipt.getId().equals(id)) // Filters receipts by ID.
        .findFirst() // Gets the first matching receipt (if any).
        .orElse(null); // Returns null if no match is found.
  }

  /**
   * Retrieves all receipts stored in the database.
   * 
   * @return A list of all ReceiptResponse objects.
   */
  public List<ReceiptResponse> findAll() {
    // Returns the entire in-memory list of receipts.
    return database;
  }

  /**
   * Saves a new receipt to the database.
   * 
   * @param receipt The ReceiptResponse object to save.
   * @return The saved ReceiptResponse object.
   */
  public ReceiptResponse save(ReceiptResponse receipt) {
    // Adds the new receipt to the in-memory database.
    database.add(receipt);
    // Returns the saved receipt object.
    return receipt;
  }
}
