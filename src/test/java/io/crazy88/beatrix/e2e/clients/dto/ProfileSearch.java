package io.crazy88.beatrix.e2e.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
@Builder
public class ProfileSearch {

    private UUID profileId;
    private String brandCode;
    private String email;
    private String nickname;

}
