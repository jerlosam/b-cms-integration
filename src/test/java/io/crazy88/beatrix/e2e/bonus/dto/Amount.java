package io.crazy88.beatrix.e2e.bonus.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Amount {
    String type;
    BigDecimal value;
}
