package io.crazy88.beatrix.e2e.bonus.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class Description {

    String internal;
    Map<String, ExternalDescription> external;
    String bonusType;
//    String category;
}
