package io.crazy88.beatrix.e2e.rewardmanager.loyalty.clients.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Wither;

@Data
@Wither
@Getter
@Builder
@AllArgsConstructor
public class BrandRegion {

    @NotNull
    String name;

    @NotNull
    UUID id;
}
