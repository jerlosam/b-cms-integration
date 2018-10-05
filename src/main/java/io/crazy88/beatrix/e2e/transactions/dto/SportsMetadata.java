package io.crazy88.beatrix.e2e.transactions.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class SportsMetadata extends Metadata {

    private SportsData data;

    @Builder
    public SportsMetadata(SportsData data, String type) {
        super(type);
        this.data = data;
    }

}
