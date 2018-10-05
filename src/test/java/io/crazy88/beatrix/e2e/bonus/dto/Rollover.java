package io.crazy88.beatrix.e2e.bonus.dto;

import io.crazy88.beatrix.e2e.bonus.dto.types.RolloverBase;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class Rollover {
    UUID constraintsTemplateId;
    RolloverBase base;
    Set<RolloverFactor> factors;
}
