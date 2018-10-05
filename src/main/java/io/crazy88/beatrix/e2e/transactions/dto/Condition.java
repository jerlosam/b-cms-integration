package io.crazy88.beatrix.e2e.transactions.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Condition {

    private String action;
    
    private List<String> conditions;
}
