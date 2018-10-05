package io.crazy88.beatrix.e2e.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.util.UUID;

@Data
@Wither
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferredBy {
    private UUID profileId;
}