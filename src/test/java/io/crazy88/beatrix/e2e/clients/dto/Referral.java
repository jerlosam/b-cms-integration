package io.crazy88.beatrix.e2e.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@Wither
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Referral {
    private String trackingCode;
    private ReferredBy referredByProfile;
}