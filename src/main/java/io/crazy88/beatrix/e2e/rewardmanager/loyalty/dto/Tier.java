package io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tier {

    Map<String, String> names;

    String rewardId;

    String rewardName;

    int requiredPoints;

    int requiredCriteriaPoints;

    Map<String, Ratio> pointsToCash;
}
