package io.crazy88.beatrix.e2e.bonus.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Set;

@Value
@Builder
public class Availability {
    String brandCode;
    Instant endsAt;
    String regionCode;
    Instant startsAt;
    Trigger trigger;
    Set<UseRestriction> useRestrictions;
    Set<String> currencies;
}
