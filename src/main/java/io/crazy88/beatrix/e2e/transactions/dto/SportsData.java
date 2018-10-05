package io.crazy88.beatrix.e2e.transactions.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class SportsData {

    private String txType;

    private String betDescription;

    private String currencyCode;
    
    private BigDecimal risk;
    
    private BigDecimal win;
    
    private String outcome;

    private List<Selection> selections;
    
}
