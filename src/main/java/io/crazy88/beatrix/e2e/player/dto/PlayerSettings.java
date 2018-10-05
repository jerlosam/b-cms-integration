package io.crazy88.beatrix.e2e.player.dto;

import internal.katana.selenium.BrowserManager;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.JavascriptExecutor;

import java.util.ArrayList;
import java.util.Arrays;

@Data
@Builder
@EqualsAndHashCode
public class PlayerSettings {

    public String email;

    public String phone;

    public String nickname;

    public String firstName;

    public String lastName;

    public String city;

    public String postalCode;

    public String addressLine;

    public String language;

    public String timezone;

    public static PlayerSettings generateRandomPlayerSettingsData(BrowserManager browser) {
        String random = RandomStringUtils.randomAlphanumeric(15);

        ArrayList<String> languages = (ArrayList<String>) ((JavascriptExecutor)browser.driver()).executeScript(" return config.languages");

        String language = languages.stream()
                .filter(lang  -> !lang.equalsIgnoreCase("en"))
                .findFirst().get();

        return PlayerSettings.builder()
                .email(String.format("%s@4null.com", random))
                .phone("5666669999")
                .nickname(random)
                .firstName(random)
                .lastName(random)
                .postalCode("00901")
                .city("San Juan")
                .addressLine("1 Calle San Gerónimo, San Juan, 00901, Puerto Rico")
                //player are created by default in English
                .language(language)
                //player are created by default in EST
                .timezone("Eastern European Time")
                .build();
    }

}
