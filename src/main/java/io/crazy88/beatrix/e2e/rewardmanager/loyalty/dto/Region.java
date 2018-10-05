package io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String name;
}
