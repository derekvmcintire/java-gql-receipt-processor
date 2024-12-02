package ReceiptProcessor.infrastructure.repository;

import org.springframework.stereotype.Repository;
import ReceiptProcessor.domain.model.Receipt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository // Marks this class as a Spring-managed bean, making it available for dependency
            // injection.
public class ReceiptRepository {

  // In-memory storage for receipts using a thread-safe ConcurrentHashMap.
  private final Map<String, Receipt> receiptStore = new ConcurrentHashMap<>();

  /**
   * Saves a receipt to the in-memory store.
   *
   * @param receipt the receipt to be saved.
   * @return the saved receipt.
   */
  public Receipt save(Receipt receipt) {
    // Store the receipt in the in-memory map with the receipt ID as the key.
    receiptStore.put(receipt.getId(), receipt);
    // Return the saved receipt to the caller.
    return receipt;
  }

  /**
   * Retrieves a receipt by its ID from the in-memory store.
   *
   * @param id the ID of the receipt to be fetched.
   * @return the receipt with the specified ID, or null if not found.
   */
  public Receipt findById(String id) {
    // Retrieve the receipt from the map using the receipt ID.
    // If the receipt with the given ID doesn't exist, it returns null.
    return receiptStore.get(id);
  }

  /**
   * Retrieves all receipts from the in-memory store.
   *
   * @return a list of all receipts in the store.
   */
  public List<Receipt> findAll() {
    // Convert the values of the receiptStore map (which are Receipt objects) to a
    // List.
    return new ArrayList<>(receiptStore.values());
  }
}
