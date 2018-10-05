package io.crazy88.beatrix.e2e.player.components;

import internal.katana.selenium.BrowserManager;
import internal.katana.selenium.core.browser.BrowserVendor;
import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReferAFriendComponent {

    @Autowired
    BrowserManager browser;

    @Autowired
    private BrowserNavigation browserNavigation;

    @Autowired
    BrowserValidation browserValidations;

    @Autowired
    private BrowserWait browserWait;

    private static final By REFER_A_FRIEND_CTA_BUTTON = By.cssSelector("article.raf-intro > section.intro-section > a.custom-cta");

    private static final By COMPONENT = By.tagName("bx-raf");

    private static final By TITLE = By.cssSelector("bx-overlay-header h2");

    private static final By SHARING_LINKS = By.cssSelector("bx-raf-links");

    public void navigateToLogin(){
        browserNavigation.navigateWithElement(REFER_A_FRIEND_CTA_BUTTON);
        browserWait.waitForElement(LoginComponent.OVERLAY_HEADER);
    }

    public boolean isTitleDisplayed() {
        if (browser.driver().findElements(TITLE).size() != 1) {
            return false;
        }
        if (browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI) {
            return true;
        }
        String titleText = browser.driver().findElement(TITLE).getText().toUpperCase();
        return titleText.equals("REFER-A-FRIEND BONUS");
    }

    public boolean isSharingLinksDisplayed() {
        return browser.driver().findElements(SHARING_LINKS).size() == 1;
    }

    public boolean isRAFButtonDisplayed() {
        return browser.driver().findElements(REFER_A_FRIEND_CTA_BUTTON).size() > 0;
    }

    public String copyLink() {
        String copyLink = browser.driver().findElement(By.cssSelector(".copy-url-cell input")).getAttribute("value");
        return copyLink;
    }

}
