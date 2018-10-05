package io.crazy88.beatrix.e2e.player.actions;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.player.components.PlayerSettingsComponent;
import io.crazy88.beatrix.e2e.player.form.profileupdate.ProfileUpdateFields;
import io.crazy88.beatrix.e2e.player.dto.PlayerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;

@Component
public class PlayerProfileSettingsActions {

    @Autowired
    private PlayerSettingsComponent playerSettingsComponent;

    @Autowired
    private BrowserForm browserForm;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browserManager;

    @Autowired
    private Reporter reporter;

    public void updateSettings(PlayerSettings settings, String password, List<String> profileUpdateFields) {
        String url = browserManager.driver().getCurrentUrl();
         playerSettingsComponent.fillForm(settings, profileUpdateFields);

        playerSettingsComponent.save();
        playerSettingsComponent.confirmPassword(password);
        browserWait.waitForUrlChange(url);
    }

    public PlayerSettings getDisplayedSettings() {
        return playerSettingsComponent.getValues();
    }

}
