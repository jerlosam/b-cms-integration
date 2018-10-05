package io.crazy88.beatrix.e2e.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.math.BigDecimal;

@Value
@Builder
@Wither
@AllArgsConstructor
public class Balance {

    private BalanceType type;

    private BigDecimal amount;

    private String currency;
}
