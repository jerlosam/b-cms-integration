package io.crazy88.beatrix.e2e.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Wither;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Wither
@Getter
@Builder
@AllArgsConstructor
public class Template {

    @NotNull
    UUID id;
}
