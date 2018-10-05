package io.crazy88.beatrix.e2e.payoutservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Instrument {
    private Long id;
    private String profileID;
    private String instrumentTypeCode;
    private String instrumentNumber;
}
