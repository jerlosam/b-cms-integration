package io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contribution {

    ContributionType type;

    TokenType tokenType;

    String token;

    Map<String, Ratio> ratios;

    String parent;

    String name;
}
