package io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ratio {

    BigDecimal amount;

    BigDecimal points;
}
