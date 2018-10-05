package io.crazy88.beatrix.e2e.player.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Wither;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;

import static java.time.Instant.parse;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Data
@Wither
@Builder
@AllArgsConstructor
public class PlayerSignup {
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String nickName;

    private Instant dateOfBirth;

    private String currency;

    private String phone;

    private String pinCode;

    private String postalCode;

    private String referredBySource;

    private String token;

    private String type;

    private String referralEmail;

    public static PlayerSignup generateRandomPlayerSignupData() {
        return generateFullPlayerSignupData();
    }

    public static PlayerSignup generateRandomRefereePlayerSignupData(String email, String trackingCode) {
        return generateFullRefereePlayerSignupData(email, trackingCode);
    }

    public static PlayerSignup generatePicassoPlayerSignupData() {
        return generateFullPlayerSignupData().withEmail("visualtesting@4null.com").withNickName("visualtesting").withFirstName("Visualtest").withLastName("Visualtest");
    }

    public static PlayerSignup generateRandomPlayerSignupData(final String currency) {
        return generateFullPlayerSignupData().withCurrency(currency);
    }

    private static PlayerSignup generateFullPlayerSignupData() {
        String nickName = randomAlphanumeric(30);
        return PlayerSignup.builder()
                .email(String.format("%s@4null.com", nickName))
                .password("Testing1")
                .firstName("Test " + randomAlphabetic(10))
                .lastName("User " + randomAlphabetic(10))
                .dateOfBirth(parse("1986-11-21T00:00:00Z"))
                .phone("5666666666")
                .pinCode("1234")
                .postalCode("00908")
                .nickName(nickName).build();

    }

    private static PlayerSignup generateFullRefereePlayerSignupData(String referralEmail, String token) {
        String nickName = randomAlphanumeric(30);
        return PlayerSignup.builder()
                .email(String.format("%s@4null.com", nickName))
                .password("Testing1")
                .firstName("Test " + randomAlphabetic(10))
                .lastName("User " + randomAlphabetic(10))
                .dateOfBirth(parse("1986-11-21T00:00:00Z"))
                .phone("5666666666")
                .pinCode("1234")
                .postalCode("00908")
                .nickName(nickName)
                .referredBySource("COPY")
                .token(token)
                .type("PROFILE")
                .referralEmail(referralEmail).build();

    }
}
