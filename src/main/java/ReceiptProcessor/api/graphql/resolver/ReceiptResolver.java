package ReceiptProcessor.api.graphql.resolver;

import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.api.dto.ReceiptPointsResponse;
import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.application.usecases.AddReceiptUseCase;
import ReceiptProcessor.application.usecases.GetReceiptPointsUseCase;
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
  private final GetReceiptPointsUseCase getReceiptPointsUseCase;
  private final ListReceiptsUseCase listReceiptsUseCase;
  private final AddReceiptUseCase addReceiptUseCase;

  public ReceiptResolver(
      GetReceiptUseCase getReceiptUseCase,
      GetReceiptPointsUseCase getReceiptPointsUseCase,
      ListReceiptsUseCase listReceiptsUseCase,
      AddReceiptUseCase addReceiptUseCase) {
    this.getReceiptUseCase = getReceiptUseCase;
    this.getReceiptPointsUseCase = getReceiptPointsUseCase;
    this.listReceiptsUseCase = listReceiptsUseCase;
    this.addReceiptUseCase = addReceiptUseCase;
  }

  @QueryMapping
  public ReceiptResponse getReceipt(@Argument String id) {
    return getReceiptUseCase.execute(id);
  }

  @QueryMapping
  public ReceiptPointsResponse getPoints(@Argument String id) {
    return getReceiptPointsUseCase.execute(id);
  }

  @QueryMapping
  public List<ReceiptResponse> listReceipts() {
    return listReceiptsUseCase.execute();
  }

  @MutationMapping
  public ReceiptResponse addReceipt(@Argument AddReceiptInput input) {
    return addReceiptUseCase.execute(input);
  }
}