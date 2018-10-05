package io.crazy88.beatrix.e2e.player;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.player.actions.PlayerAccountMenuActions;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import io.crazy88.beatrix.e2e.player.actions.PlayerProfileSettingsActions;
import io.crazy88.beatrix.e2e.player.clients.SiteConfigClient;
import io.crazy88.beatrix.e2e.player.dto.PlayerSettings;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import io.crazy88.beatrix.e2e.player.form.profileupdate.ProfileUpdateFields;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class ProfileSettingsSteps {

    private static final String PLAYER_SETTINGS_CONTEXT = "PLAYER_SETTINGS_CONTEXT";

    @Autowired
    private SiteConfigClient siteConfigClient;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Autowired
    private PlayerAccountMenuActions playerAccountMenuActions;

    @Autowired
    private PlayerProfileSettingsActions playerProfileSettingsActions;

    @Autowired
    private BrowserNavigation browserNavigation;

    @Autowired
    private BrowserManager browserManager;

    @Autowired
    private BrowserForm browserForm;

    @Autowired
    private Reporter reporter;

    @When("a player updates his settings")
    @ToContext(PLAYER_SETTINGS_CONTEXT)
    public PlayerSettings whenPlayerUpdateSettings(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup player) {

        playerNavigationActions.enterSite();
        playerNavigationActions.logIn(player.getEmail(), player.getPassword());
        playerNavigationActions.navigateToAccountMenu();
        playerAccountMenuActions.navigateToProfileSettings();

        PlayerSettings playerSettings = PlayerSettings.generateRandomPlayerSettingsData(browserManager);

        playerProfileSettingsActions.updateSettings(playerSettings, player.getPassword(), getProfileUpdateFields());

        return playerSettings;

    }

    @Then("the player settings are updated")
    public void thenPlayerSettingsUpdated(@FromContext(PLAYER_SETTINGS_CONTEXT) PlayerSettings playerSettings) {

        PlayerSettings currentSettings = playerProfileSettingsActions.getDisplayedSettings();

        reporter.info("CURRENT SETTINGS");
        reporter.debug(currentSettings.toString());
        reporter.info("SETTINGS");
        reporter.debug(playerSettings.toString());

        assertThat(EnumSet.allOf(ProfileUpdateFields.class).stream()
                .filter(field  -> field.exist(browserForm))
                .allMatch(formField -> formField.equals(playerSettings, currentSettings))).isTrue();
    }

    private List<String> getProfileUpdateFields() {
        return siteConfigClient
                .getProfileUpdateConfig("PT")
                .getProfileUpdateFormFields()
                .stream()
                .flatMap(field -> Arrays.asList(field.split(",")).stream())
                .collect(Collectors.toList());
    }
}
