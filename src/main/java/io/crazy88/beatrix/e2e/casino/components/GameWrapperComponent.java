package io.crazy88.beatrix.e2e.casino.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameWrapperComponent {

    private static final String CASINO_GAME_URL = "/static/";

    private static By GAME_WRAPPER_OVERLAY = By.cssSelector(".game-wrapper-overlay-container");
    private static By PLAY_FOR_REAL_BUTTON = By.cssSelector(".custom-cta.primary");

    public static By IFRAME = By.cssSelector("section.modal-body");

    private static By IFRAME_IFRAME = By.cssSelector("section.modal-body > iframe");

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserValidation browserValidations;

    @Autowired
    private BrowserWait browserWaits;

    public boolean isDisplayed(){
        if(!browser.browserDriver().browser().isDesktop()) {
            return browser.driver().getCurrentUrl().contains(CASINO_GAME_URL);
        } else {
            return browserValidations.isElementDisplayed(GAME_WRAPPER_OVERLAY);
        }
    }

    public void switchToRealMode() {
        browser.fluent().button(PLAY_FOR_REAL_BUTTON).click();
        browserWaits.waitForElement(IFRAME);
    }

    // Better if add iframe and swithTo to FluentWebElement
    public void switchToGameFrame() {
        browserWaits.waitForElement(IFRAME_IFRAME);
        WebElement iframe = browser.driver().findElement(IFRAME_IFRAME);
        browser.driver().switchTo().frame(iframe);
    }
}
