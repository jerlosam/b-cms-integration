package io.crazy88.beatrix.e2e.player.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.player.components.ContactUsComponent;

@Component
public class ContactUsActions {

    @Autowired
    private E2EProperties properties;

    @Autowired
    protected BrowserNavigation browserNavigation;

    @Autowired
    protected BrowserWait browserWait;

    @Autowired
    private ContactUsComponent contactUs;

    public void navigateToContactUsPage() {
        browserNavigation.navigateToUrl(properties.getPlayerContactUsUrl());
        browserWait.waitForElement(ContactUsComponent.COMPONENT);
    }

    public boolean isContactUsDisplayed() {
        return contactUs.isDisplayed();
    }

    public void closeContactUs() {
        contactUs.close();
    }

    public void clickOnHeader() {
        contactUs.clickOnHeader();
    }

    public void clickOnEmailField() {
        contactUs.clickOnEmailField();
    }

}
