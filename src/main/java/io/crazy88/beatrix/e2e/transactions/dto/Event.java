package io.crazy88.beatrix.e2e.transactions.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class Event {

    private String description;

    private String path;
    
    private Instant date;
    
    private String note;

}
