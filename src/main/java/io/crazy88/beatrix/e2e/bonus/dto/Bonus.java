package io.crazy88.beatrix.e2e.bonus.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bonus {

    UUID bonusId;
    String internalCode;
    String internalName;
    String externalName;
    List<String> codes;
    String description;
    String startDate;
    String endDate;
    String daysAfterRedemption;
    String expiredDate;
    Map<String, BigDecimal> amounts;
    BigDecimal lockAmount;
    int rolloverBase;
    BigDecimal factor;
    UUID templateId;
    String templateName;
    int bonusPriority;
}
