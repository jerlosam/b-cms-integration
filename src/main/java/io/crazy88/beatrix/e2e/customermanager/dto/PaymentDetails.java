package io.crazy88.beatrix.e2e.customermanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDetails {
    public String title;
    public String accountNumber;
    public String paymentType;
    public String instrumentType;
    public String amount;
}
