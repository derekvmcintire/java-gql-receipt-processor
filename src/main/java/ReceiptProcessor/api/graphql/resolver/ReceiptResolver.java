package ReceiptProcessor.api.graphql.resolver;

import ReceiptProcessor.api.dto.AddReceiptInput;
import ReceiptProcessor.api.dto.ReceiptResponse;
import ReceiptProcessor.application.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReceiptResolver {

  @Autowired
  private ReceiptService receiptService;

  @QueryMapping
  public ReceiptResponse getReceipt(@Argument String id) {
    return receiptService.getReceipt(id);
  }

  @QueryMapping
  public List<ReceiptResponse> listReceipts() {
    return receiptService.listReceipts();
  }

  @MutationMapping
  public ReceiptResponse addReceipt(@Argument AddReceiptInput input) {
    return receiptService.addReceipt(input);
  }
}