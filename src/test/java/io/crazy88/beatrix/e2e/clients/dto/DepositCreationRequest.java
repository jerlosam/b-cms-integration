package io.crazy88.beatrix.e2e.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
@AllArgsConstructor
public class DepositCreationRequest {

    private String accountNumber;
    private String paymentType;
    private String paymentMethod;
    private String instrumentTypeCode;
    private PaymentEntrySource entrySource;

}
