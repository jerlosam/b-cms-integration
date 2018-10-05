package io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@Builder
@Wither
@NoArgsConstructor
@AllArgsConstructor
public class Program implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;

    private Map<String, String> names;

    private String link;

    private ProgramStatus status;

    private String brand;

    private Brand brandForTesting;

    private Region region;

    private Boolean lifetime;

    private Map<String, String> rewardPointsNames;

    private Long rewardPointsLifespan;

    private PeriodStartType periodStartType;

    private Integer periodLength;

    private Integer periodStartingDay;

    private List<String> countries;

    private String currencyTemplate;

    private List<String> currencies;

    private List<String> products;

    private List<Contribution> contributions;

    private List<Tier> tiers;

    private Instant validFrom;

    private Instant validTo;

}