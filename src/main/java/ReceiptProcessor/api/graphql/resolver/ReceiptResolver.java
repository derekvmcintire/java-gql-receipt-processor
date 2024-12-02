package ReceiptProcessor.api.graphql.resolver;

import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.application.usecases.AddReceiptUseCase;
import ReceiptProcessor.application.usecases.GetReceiptUseCase;
import ReceiptProcessor.application.usecases.ListReceiptsUseCase;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReceiptResolver {

  private final GetReceiptUseCase getReceiptUseCase;
  private final ListReceiptsUseCase listReceiptsUseCase;
  private final AddReceiptUseCase addReceiptUseCase;

  public ReceiptResolver(
      GetReceiptUseCase getReceiptUseCase,
      ListReceiptsUseCase listReceiptsUseCase,
      AddReceiptUseCase addReceiptUseCase) {
    this.getReceiptUseCase = getReceiptUseCase;
    this.listReceiptsUseCase = listReceiptsUseCase;
    this.addReceiptUseCase = addReceiptUseCase;
  }

  @QueryMapping
  public ReceiptResponse getReceipt(@Argument String id) {
    return getReceiptUseCase.execute(id);
  }

  @QueryMapping
  public List<ReceiptResponse> listReceipts() {
    return listReceiptsUseCase.execute();
  }

  @MutationMapping
  public ReceiptResponse addReceipt(@Argument AddReceiptInput input) {
    ReceiptResponse response = addReceiptUseCase.execute(input);
    System.out.println("************Response points in resolver: " +
        response.getPoints());
    return response;
  }
}