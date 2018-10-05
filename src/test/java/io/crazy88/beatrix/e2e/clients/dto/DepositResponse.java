package io.crazy88.beatrix.e2e.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Value
@Getter
@Builder
@AllArgsConstructor
public class DepositResponse {
    private String id;
    private String status;
    private UUID profileId;
    private String currencyCode;
    private int amountInCents;
    private String date;
    private int expireSeconds;
    private String billingDescriptor;
    private int amountInCentsChargedPlayer;
    private String currencyChargedPlayer;
    private UUID transactionID;
    private String paymentType;
    private int feeInCents;
    private int amountMinusFeeInCents;
    private String instrumentTypeCode;
}
