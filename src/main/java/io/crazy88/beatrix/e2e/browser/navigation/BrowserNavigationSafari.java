package io.crazy88.beatrix.e2e.browser.navigation;

import internal.katana.selenium.core.browser.BrowserVendor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("safari")
public class BrowserNavigationSafari extends BrowserNavigation {

    public void scrollToBottom() {
    }

    public void scrollToTop() {
    }

    public void scrollToElement(By locator, boolean top) {
    }

    public void  navigateWithElement(By element) {
        String currentUrl = browser.driver().getCurrentUrl();
        browserWaits.waitForElement(element);
        browser.driver().findElement(element).click();
        browserWaits.waitForUrlChange(currentUrl);
        browserWaits.waitForAngular();
    }
}