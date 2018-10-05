package io.crazy88.beatrix.e2e.customermanager.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class PlayerBalances {
	public BigDecimal totalCash;
	public BigDecimal totalBalance;
	public Map<String, BigDecimal> detailedBalances;
}
