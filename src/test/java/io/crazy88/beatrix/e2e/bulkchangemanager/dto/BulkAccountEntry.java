package io.crazy88.beatrix.e2e.bulkchangemanager.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
@Wither
@AllArgsConstructor
public class BulkAccountEntry {

    private UUID profileId;

    private String categoryCode;

    private String transactionRef;

    private String externalDescription;

    private String amount;
}
