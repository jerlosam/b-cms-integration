package io.crazy88.beatrix.e2e.player.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReferralSignup {

    private String referredBySource;
    private String token;
    private String type;
}
