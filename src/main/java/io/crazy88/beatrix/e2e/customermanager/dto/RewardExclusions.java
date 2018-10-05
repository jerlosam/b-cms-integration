package io.crazy88.beatrix.e2e.customermanager.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RewardExclusions {
    private Map<String, Boolean> rewardExclusions;
}
