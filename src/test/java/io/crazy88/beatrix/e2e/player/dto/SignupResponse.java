package io.crazy88.beatrix.e2e.player.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
@AllArgsConstructor
public class SignupResponse {
    String sessionId;
}
