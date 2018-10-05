package io.crazy88.beatrix.e2e.player.components;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.player.form.profileupdate.ProfileUpdateFields;
import io.crazy88.beatrix.e2e.player.dto.PlayerSettings;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Component
public class PlayerSettingsComponent {

    private static final By SAVE_BTN = By.id("profileUpdateAccount-submit");

    private static final By CONFIRM_PASSWORD_FLD = By.id("profileUpdateConfirmation-password");

    private static final By NOTIFICATION_OVERLAY = By.className("custom-notification");

    @Autowired
    private BrowserManager browserManager;

    @Autowired
    private BrowserForm browserForm;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private Reporter reporter;

    private static final Map<String, ProfileUpdateFields> FORM_FIELDS_MAP;

    static {
        FORM_FIELDS_MAP = new HashMap<>();
        for (ProfileUpdateFields formField : EnumSet.allOf(ProfileUpdateFields.class)) {
            FORM_FIELDS_MAP.put(formField.getFieldName(), formField);
        }
    }

    public void fillForm(PlayerSettings playerSettings, List<String> fields) {
        List<String> allFields = new ArrayList<>(fields);

        addFixedFields(allFields);

        allFields.stream()
                .filter(field  -> FORM_FIELDS_MAP.containsKey(field))
                .forEach(field -> {
                    FORM_FIELDS_MAP.get(field).fillFormField(browserForm, playerSettings);
                } );
    }

    private void addFixedFields(List<String> fields) {
        if (!fields.contains(ProfileUpdateFields.LANGUAGES.getFieldName())) {
            fields.add(ProfileUpdateFields.LANGUAGES.getFieldName());
        }

        if (!fields.contains(ProfileUpdateFields.TIMEZONES.getFieldName())) {
            fields.add(ProfileUpdateFields.TIMEZONES.getFieldName());
        }
    }

    public boolean isDisplayed() {
        return !browserManager.driver().findElements(SAVE_BTN).isEmpty();
    }

    public void save() {
        browserForm.submitForm();
    }

    public void confirmPassword(String password) {
        browserForm.fillTextField(CONFIRM_PASSWORD_FLD, password);
        browserForm.submitFormInContainer(NOTIFICATION_OVERLAY);
    }

    public PlayerSettings getValues() {
        final PlayerSettings playerSettings = PlayerSettings.builder().build();

        FORM_FIELDS_MAP.entrySet().stream()
                .map(entry -> entry.getValue())
                .filter(field  -> field.exist(browserForm))
                .forEach(field -> field.fillPlayerSetting(browserForm, playerSettings));

        return playerSettings;
    }
}
