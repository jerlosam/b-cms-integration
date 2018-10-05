package io.crazy88.beatrix.e2e.player.components;

import internal.katana.selenium.BrowserManager;
import internal.katana.selenium.core.browser.BrowserVendor;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PinCodeComponent {

    private static final By PIN_WRAPPER = By.cssSelector(".pin-wrapper");

    private static final By PIN_FIELD = By.cssSelector(".pin-input");

    @Autowired
    private BrowserForm browserForms;

    @Autowired
    private BrowserWait browserWaits;

    @Autowired
    private BrowserManager browser;

    public void createPinCode() {
        browserForms.getAllElements(PIN_FIELD).stream()
                .forEach(webElement -> {
                    webElement.sendKeys("1");
                });
        if(browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI) {
            //TODO there is a bug in safari selenium, it says that the button is not visible when it actually is.
            browser.driver().findElement(PIN_FIELD).sendKeys(Keys.ENTER);
        } else {
            browserForms.submitForm();
        }
    }

    public void waitForPinCodeNotDisplayed(){
        browserWaits.waitForElementNotDisplayed(PIN_WRAPPER);
    }

    public void waitForPinCodeDisplayed(){
        browserWaits.waitForElement(PIN_WRAPPER);
    }

    public boolean isPinCodeOverlayDisplayed() {
        return !browser.driver().findElements(PIN_WRAPPER).isEmpty() &&
                browser.driver().findElement(PIN_WRAPPER).isDisplayed();
    }
}
