package io.crazy88.beatrix.e2e.player.form.profileupdate;

import io.crazy88.beatrix.e2e.player.form.FormId;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
final class ProfileUpdateId extends FormId {

    private static final String FIELD_PREFIX = "profileUpdateAccount-";

    public static final By EMAIL_FIELD = By.id(FIELD_PREFIX + EMAIL);

    public static final By PHONE_FIELD = By.id(FIELD_PREFIX + PHONE);

    public static final By NICKNAME_FIELD = By.id(FIELD_PREFIX + NICKNAME);

    public static final By FIRSTNAME_FIELD = By.id(FIELD_PREFIX + FIRSTNAME);

    public static final By LASTNAME_FIELD = By.id(FIELD_PREFIX + LASTNAME);

    public static final By CITY_CODE_FIELD = By.id(FIELD_PREFIX + CITY);

    public static final By POSTAL_CODE_FIELD = By.id(FIELD_PREFIX + POSTAL_CODE);

    public static final By ADDRESS_LINE_FIELD = By.id(FIELD_PREFIX + ADDRESS_LINE);

    public static final By COUNTRY_FIELD = By.id(FIELD_PREFIX + COUNTRY);

    public static final By LANGUAGES_FIELD = By.id(FIELD_PREFIX + LANGUAGES);

    public static final By LANGUAGES_DROPDOWN = By.xpath("//input[@id='" + FIELD_PREFIX + LANGUAGES + "']/../..");

    public static final By TIMEZONES_FIELD = By.id(FIELD_PREFIX + TIMEZONES);

    public static final By TIMEZONES_DROPDOWN = By.xpath("//input[@id='" + FIELD_PREFIX + TIMEZONES + "']/../..");

}
