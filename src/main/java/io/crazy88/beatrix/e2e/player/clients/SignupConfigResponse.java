package io.crazy88.beatrix.e2e.player.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
@Builder
public class SignupConfigResponse {

    @JsonProperty("signupform.fields")
    List<String> signupFormFields;

    @JsonProperty("signupform.optionalfields")
    List<String> signupFormOptionalFields;

    @JsonProperty("currencies.default")
    String defaultCurrency;

    @JsonProperty("phoneRegex")
    String phoneRegex;

    @JsonProperty("phonePrefix")
    String phonePrefix;
}
