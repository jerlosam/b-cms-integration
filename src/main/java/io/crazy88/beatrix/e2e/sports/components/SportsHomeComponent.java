package io.crazy88.beatrix.e2e.sports.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

import internal.katana.selenium.BrowserManager;

@Component
public class SportsHomeComponent {

    private static final By ALL_SPORTS_BUTTON_SELECTOR = By.id("sec-menu-btn");

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browserManager;

    public boolean isHomeComponentDisplayed() {
        browserWait.waitForAngular();
        browserWait.waitForPageToBeLoadedCompletely();
        return (!browserManager.driver().findElements(ALL_SPORTS_BUTTON_SELECTOR).isEmpty());
    }
}
