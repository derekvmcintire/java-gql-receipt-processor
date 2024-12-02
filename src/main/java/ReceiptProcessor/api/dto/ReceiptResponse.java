package ReceiptProcessor.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ReceiptResponse {
  private String id;
  private String store;
  private String date;
  private double total;
  private int points;
  private List<ItemResponse> items;
}
