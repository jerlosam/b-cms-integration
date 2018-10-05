package io.crazy88.beatrix.e2e.transactions.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class TransactionBatchCreated {

    @NotNull
    private List<Balance> balances;
}
