package io.crazy88.beatrix.e2e.bonus.dto;

import io.crazy88.beatrix.e2e.bonus.dto.types.PriorityType;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class Funds {

    Map<String, Amount> amounts;
    Expiration expiration;
    Amount lockAmount;
    PriorityType usagePriority;
    Rollover rollover;

}
