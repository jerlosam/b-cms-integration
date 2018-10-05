package io.crazy88.beatrix.e2e.bonus.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class Expiration {
    Instant expiresOnDate;
    Integer expiresInDays;
}
