package io.crazy88.beatrix.e2e.customermanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.List;

@Value
@Builder
@Wither
@AllArgsConstructor
public class ManualAccountEntry {

    private List<String> categories;
    private String transactionRef;
    private String externalDescription;
    private String internalDescription;
    private String amount;
}
