package io.crazy88.beatrix.e2e.bonus.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExternalDescription {
    String name;
    String description;
}
