package io.crazy88.beatrix.e2e.browser.navigation;

import org.openqa.selenium.By;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("edge")
public class BrowserNavigationEdge extends BrowserNavigation {

    public void navigateWithElement(By element) {
        String currentUrl = browser.driver().getCurrentUrl();
        browserWaits.waitForElement(element);
        browser.driver().findElement(element).click();
        browserWaits.waitForUrlChange(currentUrl);
        browserWaits.waitForAngular();
    }
}