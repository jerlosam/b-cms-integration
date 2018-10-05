package io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto;

import lombok.Getter;

@Getter
public enum Brand {

    CRAZY88("Crazy 88");

    private String label;

    Brand(final String label) {
        this.label = label;
    }
}
