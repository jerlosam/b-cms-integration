package io.crazy88.beatrix.e2e.rewardmanager.bonus.components;

import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingsComponent {

    private static final By NEW_TEMPLATE_BUTTON = By.id("createNewTemplate");

    @Autowired
    private BrowserNavigation browserNavigations;

    public void newTemplate() {
        browserNavigations.navigateWithElement(NEW_TEMPLATE_BUTTON);
    }
}
