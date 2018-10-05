package io.crazy88.beatrix.e2e.bonus.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Map;
import java.util.Set;

@Value
@Builder
public class Trigger {

//    String type;
    Map<String, Amount> minAmount;
    Set<String> paymentMethods;

}
