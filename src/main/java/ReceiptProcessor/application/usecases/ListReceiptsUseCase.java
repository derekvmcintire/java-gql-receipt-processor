package ReceiptProcessor.application.usecases;

import ReceiptProcessor.domain.model.Receipt;
import ReceiptProcessor.infrastructure.repository.ReceiptRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // Marks this class as a Spring-managed bean for dependency injection.
public class ListReceiptsUseCase {

  private final ReceiptRepository receiptRepository; // Declares the dependency on the ReceiptRepository.

  // Constructor to inject the ReceiptRepository dependency into
  // ListReceiptsUseCase.
  public ListReceiptsUseCase(ReceiptRepository receiptRepository) {
    this.receiptRepository = receiptRepository; // Spring will automatically provide the repository when creating
                                                // the use case.
  }

  // The execute() method is the entry point for this use case.
  // It retrieves all receipts from the repository.
  public List<Receipt> execute() {
    // Step 1: Call the findAll() method on the repository to retrieve all receipts.
    // The findAll() method returns a list of all receipts stored in the repository.
    return receiptRepository.findAll();
  }
}
