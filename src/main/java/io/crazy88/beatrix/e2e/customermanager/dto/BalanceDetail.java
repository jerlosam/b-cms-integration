package io.crazy88.beatrix.e2e.customermanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Balance Detail
 * Created by jolalla on 7/11/17.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDetail {

    private String type;

    private BigDecimal amount;

    private String currency;
}
