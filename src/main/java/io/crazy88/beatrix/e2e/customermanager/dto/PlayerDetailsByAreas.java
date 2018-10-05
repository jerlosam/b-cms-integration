package io.crazy88.beatrix.e2e.customermanager.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PlayerDetailsByAreas {

    private String header;
    private String firstArea;
    private String secondArea;
    private String thirdArea;
    private String startDate;
}
