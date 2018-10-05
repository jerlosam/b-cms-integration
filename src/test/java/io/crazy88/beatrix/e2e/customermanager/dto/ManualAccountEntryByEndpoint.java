package io.crazy88.beatrix.e2e.customermanager.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

/**
 * Manual Account Adjustment DTO
 * Created by jolalla on 7/4/17.
 */
@Value
@Builder
public class ManualAccountEntryByEndpoint {

    private Float amount;

    private String correlationRef;

    private String currency;

    private String category;

    private String strategy;

    private Map metadata;

}
