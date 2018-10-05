package io.crazy88.beatrix.e2e.bonus.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRewardRequest {
    String brandCode;
    Availability availability;
    Description description;
    Outcome outcome;
}
