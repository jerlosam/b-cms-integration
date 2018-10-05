package io.crazy88.beatrix.e2e.rewardmanager.bonus.components;

import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BonusHeaderComponent {

    @Autowired BrowserNavigation browserNavigations;


    private static final By SETTINGS_TAB = By.cssSelector("bx-navigation a.nav-link[href=\"/bonuses/settings\"]");

    public void navigateToSettingsTab() {
        browserNavigations.navigateWithElement(SETTINGS_TAB);
    }
}
