package io.crazy88.beatrix.e2e.player.form.signup;

import io.crazy88.beatrix.e2e.player.form.FormId;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
final class ProfileRegistrationId extends FormId {

    private static final String FIELD_PREFIX = "registration-";

    public static final By NICKNAME_FIELD = By.id(FIELD_PREFIX + NICKNAME);

    public static final By EMAIL_FIELD = By.id(FIELD_PREFIX + EMAIL);

    public static final By PASSWORD_FIELD = By.id(FIELD_PREFIX + PASSWORD);

    public static final By FIRSTNAME_FIELD = By.id(FIELD_PREFIX + FIRSTNAME);

    public static final By LASTNAME_FIELD = By.id(FIELD_PREFIX + LASTNAME);

    public static final By DATEOFBIRTH_FIELD = By.id(FIELD_PREFIX + DATEOFBIRTH);

    public static final By PHONE_FIELD = By.id(FIELD_PREFIX + PHONE);

    public static final By POSTAL_CODE_FIELD = By.id(FIELD_PREFIX + POSTAL_CODE);

    public static final By CURRENCY_DROPDOWN = By.xpath("//input[@id='" + FIELD_PREFIX + CURRENCY + "']/../..");

    public static final By FOUR_DIGIT_PIN_CODE_FIELD = By.id(FIELD_PREFIX + FOUR_DIGIT_PIN_CODE);

    public static final By TERMS_CONDITION_CONTAINER = By.className("custom-checkbox");

    public static final By TERMS_AND_CONDITIONS_CHECKBOX =
            By.cssSelector("label[for='" + FIELD_PREFIX + TERMS_AND_CONDITIONS + "']");

}
