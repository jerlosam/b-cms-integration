package io.crazy88.beatrix.e2e.player.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class ReactiveChatComponent {

    @Autowired
    BrowserValidation browserValidations;

    @Autowired
    BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;

    public static final By REACTIVE_CHAT_NOW_LINK = By.id("reactive-chat-link");

    public static final By REACTIVE_CHAT_BOX = By.id("MoxieFlyoutHolder");

    public void clickOnChatNowLink() {
        browserWait.waitForAngular();
        browser.driver().findElement(REACTIVE_CHAT_NOW_LINK).click();
        browserWait.waitForAngular();
    }

    public boolean isReactiveChatDisplayed() {
        return browserValidations.isElementDisplayed(REACTIVE_CHAT_BOX);
    }

}
