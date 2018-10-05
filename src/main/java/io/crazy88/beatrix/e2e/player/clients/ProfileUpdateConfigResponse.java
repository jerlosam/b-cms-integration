package io.crazy88.beatrix.e2e.player.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
@Builder
public class ProfileUpdateConfigResponse {

    @JsonProperty("profileupdateform.fields")
    List<String> profileUpdateFormFields;

    @JsonProperty("profileupdateform.optionalfields")
    List<String> profileUpdateFormOptionalFields;

    @JsonProperty("currencies.default")
    String defaultCurrency;

    @JsonProperty("phoneRegex")
    String phoneRegex;

    @JsonProperty("phonePrefix")
    String phonePrefix;
}
