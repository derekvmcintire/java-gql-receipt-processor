// package ReceiptProcessor.application.usecases;

// import ReceiptProcessor.domain.model.Receipt;
// import ReceiptProcessor.infrastructure.repository.ReceiptRepository;
// import org.springframework.stereotype.Component;

// @Component // Marks this class as a Spring-managed bean for dependency
// injection.
// public class GetReceiptUseCase {

// private final ReceiptRepository receiptRepository; // Declares the dependency
// on the ReceiptRepository.

// // Constructor to inject the ReceiptRepository dependency into the
// // GetReceiptUseCase.
// public GetReceiptUseCase(ReceiptRepository receiptRepository) {
// this.receiptRepository = receiptRepository; // Spring will automatically
// provide the repository when creating
// // the use case.
// }

// // The execute() method is the entry point for this use case.
// // It retrieves a receipt by its ID from the repository.
// public Receipt execute(String id) {
// // Step 1: Try to fetch the receipt from the repository by its ID.
// // The findById() method returns a Receipt or null if not found.
// Receipt receipt = receiptRepository.findById(id);

// // Step 2: If the receipt is not found (receipt is null), throw an exception.
// if (receipt == null) {
// throw new IllegalArgumentException("Receipt not found.");
// }

// return receipt;
// }
// }
