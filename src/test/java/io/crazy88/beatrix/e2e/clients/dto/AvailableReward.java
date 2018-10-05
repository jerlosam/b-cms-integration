package io.crazy88.beatrix.e2e.clients.dto;


import io.crazy88.beatrix.e2e.bonus.dto.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
@Getter
@Builder
@AllArgsConstructor
public class AvailableReward {

    private Description description;
}
