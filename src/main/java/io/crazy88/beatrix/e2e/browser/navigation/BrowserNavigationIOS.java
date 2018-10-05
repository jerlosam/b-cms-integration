package io.crazy88.beatrix.e2e.browser.navigation;

import org.openqa.selenium.By;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("ios")
public class BrowserNavigationIOS extends BrowserNavigation {

    public void  navigateWithElement(By element) {
        String currentUrl = browser.driver().getCurrentUrl();
        browserWaits.waitForPageToBeLoadedCompletely();
        browserWaits.waitForElement(element);
        browser.driver().findElement(element).click();
        browserWaits.waitForUrlChange(currentUrl);
        browserWaits.waitForAngular();
        browserWaits.waitForPageToBeLoadedCompletely();
    }
}