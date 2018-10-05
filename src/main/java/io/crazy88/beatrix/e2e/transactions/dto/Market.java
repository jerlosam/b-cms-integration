package io.crazy88.beatrix.e2e.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Market {
    
    private String description;
    
    private String note;
}
