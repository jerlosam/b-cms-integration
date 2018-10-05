package io.crazy88.beatrix.e2e.bonus.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class IssueRewardRequest {
    UUID rewardId;
}
