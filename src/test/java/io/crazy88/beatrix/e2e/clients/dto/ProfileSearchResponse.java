package io.crazy88.beatrix.e2e.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
@Builder
public class ProfileSearchResponse {

    private List<ProfileSearch> items;



}
