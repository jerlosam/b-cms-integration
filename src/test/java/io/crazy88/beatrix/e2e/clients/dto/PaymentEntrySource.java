package io.crazy88.beatrix.e2e.clients.dto;

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
public class PaymentEntrySource {
    private String channelCode;
    private String ipAddress;
}
