package io.crazy88.beatrix.e2e.player.components;

import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.player.form.signup.ProfileRegistrationFields;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SignupComponent {

    public static final By OVERLAY_HEADER = By.cssSelector("bx-overlay-header header");

    public static final By OVERLAY_BODY = By.cssSelector("bx-overlay-body section");

    public static final By OVERLAY = By.tagName("bx-overlay-container");

    @Autowired
    private BrowserForm browserForms;

    @Autowired
    private BrowserWait browserWaits;

    private static final Map<String, ProfileRegistrationFields> FORM_FIELDS_MAP;

    static {
        FORM_FIELDS_MAP = new HashMap<>();
        for (ProfileRegistrationFields formField : EnumSet.allOf(ProfileRegistrationFields.class)) {
            FORM_FIELDS_MAP.put(formField.getFieldName(), formField);
        }
    }

    public void fillForm(PlayerSignup playerSignup, List<String> fields) {

        for (String field : fields) {

            if (FORM_FIELDS_MAP.containsKey(field)) {
                FORM_FIELDS_MAP.get(field).fillFormField(browserForms, playerSignup);
            }
        }
    }

    public void submitForm() {

        browserForms.submitForm();
    }

    public void waitForButtonEnabled() {

    }

}
