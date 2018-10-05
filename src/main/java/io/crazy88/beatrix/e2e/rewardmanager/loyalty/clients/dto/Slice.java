package io.crazy88.beatrix.e2e.rewardmanager.loyalty.clients.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@Wither
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Slice<T> {

    private List<T> items;
}

