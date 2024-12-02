package ReceiptProcessor.api.graphql.resolver;

import ReceiptProcessor.application.usecases.AddReceiptUseCase;
import ReceiptProcessor.application.usecases.GetReceiptUseCase;
import ReceiptProcessor.application.usecases.ListReceiptsUseCase;
import ReceiptProcessor.domain.model.Receipt;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import ReceiptProcessor.domain.model.Item;
import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.api.dto.ItemResponse;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Validated
public class ReceiptResolver {

  private final GetReceiptUseCase getReceiptUseCase;
  private final ListReceiptsUseCase listReceiptsUseCase;
  private final AddReceiptUseCase addReceiptUseCase;

  public ReceiptResolver(GetReceiptUseCase getReceiptUseCase,
      ListReceiptsUseCase listReceiptsUseCase,
      AddReceiptUseCase addReceiptUseCase) {
    this.getReceiptUseCase = getReceiptUseCase;
    this.listReceiptsUseCase = listReceiptsUseCase;
    this.addReceiptUseCase = addReceiptUseCase;
  }

  @PostConstruct
  public void init() {
    // This will be printed when the resolver bean is initialized
    System.out.println("ReceiptResolver has been initialized!");
  }

  // Get receipt by ID
  @QueryMapping
  public ReceiptResponse getReceipt(String id) {
    Receipt receipt = getReceiptUseCase.execute(id);
    return mapToReceiptResponse(receipt);
  }

  // List all receipts
  @QueryMapping
  public List<ReceiptResponse> listReceipts() {
    List<Receipt> receipts = listReceiptsUseCase.execute();
    return receipts.stream()
        .map(this::mapToReceiptResponse)
        .collect(Collectors.toList());
  }

  // Add a new receipt
  @MutationMapping(name = "addReceipt")
  public ReceiptResponse addReceipt(@Valid @Argument("input") AddReceiptInput input) {
    System.out.println("******************addReceipt resolver hit**********************");
    try {
      // Map DTO to domain model
      Receipt receipt = mapToDomainModel(input);
      System.out.println("Mapped receipt: " + receipt);

      // Call use case to save receipt
      Receipt savedReceipt = addReceiptUseCase.execute(receipt);

      // Return the saved receipt as a DTO
      return mapToReceiptResponse(savedReceipt);
    } catch (Exception e) {
      // Log the exception and return a meaningful error response
      throw new RuntimeException("Error while adding receipt: " + e.getMessage());
    }
  }

  // Helper method to map AddReceiptInput DTO to domain model
  private Receipt mapToDomainModel(AddReceiptInput addReceiptInput) {
    Receipt receipt = new Receipt();
    receipt.setTotal(addReceiptInput.getTotal());
    receipt.setItems(addReceiptInput.getItems().stream()
        .map(itemInput -> new Item(itemInput.getName(), itemInput.getQuantity(), itemInput.getPrice()))
        .collect(Collectors.toList()));
    return receipt;
  }

  // Helper method to map a domain model (Receipt) to a DTO (ReceiptResponse)
  private ReceiptResponse mapToReceiptResponse(Receipt receipt) {
    List<ItemResponse> itemResponses = receipt.getItems().stream()
        .map(item -> new ItemResponse(item.getName(), item.getQuantity(), item.getPrice()))
        .collect(Collectors.toList());

    // Include store and date when mapping to ReceiptResponse
    return new ReceiptResponse(receipt.getId(), receipt.getStore(), receipt.getDate(), receipt.getTotal(),
        itemResponses);
  }

}
