package io.crazy88.beatrix.e2e.player.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class ContactUsComponent {

    @Autowired
    BrowserValidation browserValidations;

    @Autowired
    BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;

    public static final By COMPONENT = By.tagName("bx-contact-us");

    private static final By CLOSE_BUTTON = By.cssSelector("bx-overlay-header button");

    private static final By HEADER = By.cssSelector("bx-overlay-header h2");

    private static final By EMAIL_FIELD = By.id("contactUs-email");

    public boolean isDisplayed() {
        return browserValidations.isElementDisplayed(COMPONENT);
    }

    public void close(){
        browserWait.waitForElement(CLOSE_BUTTON);
        browser.driver().findElement(CLOSE_BUTTON).click();
    }

    public void clickOnHeader(){
        browserWait.waitForElement(HEADER);
        browser.driver().findElement(HEADER).click();
    }

    public void clickOnEmailField(){
        browserWait.waitForElement(EMAIL_FIELD);
        browser.driver().findElement(EMAIL_FIELD).click();
    }

}
