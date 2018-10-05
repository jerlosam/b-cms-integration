package io.crazy88.beatrix.e2e.player.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.crazy88.beatrix.e2e.customermanager.dto.Attributes;
import io.crazy88.beatrix.e2e.customermanager.dto.Security;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.time.Instant;

@Value
@Builder
@Wither
public class SignupRequest {
    private Attributes attributes;

    @JsonProperty("address")
    private SignupAddress signupAddress;
    private String brandCode;
    private String email;
    private String nickname;
    private Security security;
    private String lastName;
    private String firstName;
    private Instant birthdate;
    private String phone;

    private ReferralSignup referral;

    private ReferralEmailSignup referralEmail;
}
