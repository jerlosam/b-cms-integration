package io.crazy88.beatrix.e2e.customermanager.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Attributes {

    String language;
    String currency;
    String pin_code;

}
