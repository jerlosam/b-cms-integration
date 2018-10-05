package io.crazy88.beatrix.e2e.transactions.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Selection {
    private String description;

    private String price;

    private BigDecimal spread;

    private String period;
    
    private String outcome;

    private Market market;

    private Event event;
    
    private Condition condition;

}
