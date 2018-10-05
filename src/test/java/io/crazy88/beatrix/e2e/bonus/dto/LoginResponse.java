package io.crazy88.beatrix.e2e.bonus.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class LoginResponse {
    String sessionId;
}
