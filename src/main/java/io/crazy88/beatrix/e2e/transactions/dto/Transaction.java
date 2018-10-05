package io.crazy88.beatrix.e2e.transactions.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
@Builder
@AllArgsConstructor
public class Transaction {

    @NotEmpty
    private String transactionId;

    @NotNull
    private UUID profileId;

    @NotEmpty
    private String category;

    @NotNull
    @DecimalMin("0")
    private BigDecimal amount;

    private BigDecimal amountRisked;

    private String currency;

    private String correlationRef;

    private Instant resolutionDate;

    private List<Map<String, String>> context;

    private Metadata metadata;
}
