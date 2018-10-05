package io.crazy88.beatrix.e2e.payoutservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayoutRequest {
    String id;
    String profileId;
    String amountRequested;
    String currency;
    String paymentMethodRequested;
    Long instrumentId;
}
