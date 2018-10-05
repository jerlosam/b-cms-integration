package io.crazy88.beatrix.e2e.player.components;

import internal.katana.selenium.BrowserManager;
import internal.katana.selenium.core.browser.BrowserVendor;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TabContainerComponent {

    @Autowired BrowserManager browser;

    @Autowired BrowserWait browserWaits;

    @Autowired BrowserNavigation browserNavigations;

    public static By TAB_CONTAINER = By.cssSelector("bx-game-categories section");

    private static By TABS = By.cssSelector("bx-game-categories ul li");

    public void navigateToTab(int index) {
        browserNavigations.scrollToElement(TAB_CONTAINER, true);
        if(browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI) {
            JavascriptExecutor js = (JavascriptExecutor) browser.driver();
            js.executeScript("document.querySelector(\"bx-game-categories ul li:nth-child(" + (index + 1) + ")\").click();");
        } else {
            browser.driver().findElements(TABS).get(index).click();
        }
    }

}
