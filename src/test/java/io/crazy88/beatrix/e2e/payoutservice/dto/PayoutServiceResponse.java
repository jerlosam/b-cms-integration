package io.crazy88.beatrix.e2e.payoutservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class PayoutServiceResponse {
    String id;
    String profileId;
    String paymentMethodRequested;
    String currency;
    String amountRequested;
}
