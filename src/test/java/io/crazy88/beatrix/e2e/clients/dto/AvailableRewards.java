package io.crazy88.beatrix.e2e.clients.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Wither;

import java.util.List;

@Value
@Wither
@Getter
@Builder
@AllArgsConstructor
public class AvailableRewards {

    @JsonProperty("items")
    private List<AvailableReward> availableRewards;
}
