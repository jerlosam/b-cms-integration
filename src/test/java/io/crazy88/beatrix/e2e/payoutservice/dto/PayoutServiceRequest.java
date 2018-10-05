package io.crazy88.beatrix.e2e.payoutservice.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PayoutServiceRequest {
    String profileId;
    String amountRequested;
    String paymentMethodRequested;
}
