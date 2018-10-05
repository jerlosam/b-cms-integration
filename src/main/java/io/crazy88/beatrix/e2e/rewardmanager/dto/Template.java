package io.crazy88.beatrix.e2e.rewardmanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Template {

    String templateName;
    int casinoContribution;
}
