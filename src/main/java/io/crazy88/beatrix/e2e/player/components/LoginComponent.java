package io.crazy88.beatrix.e2e.player.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginComponent {

    public static final By OVERLAY_HEADER = By.cssSelector("bx-overlay-header");

    public static final By OVERLAY = By.tagName("bx-overlay-container");

    private static final By USERNAME_FIELD = By.id("username");

    private static final By PASSWORD_FIELD = By.id("password");

    private static final By FORGOT_PASSWORD_LINK = By.id("forgot-password");

    @Autowired
    private BrowserForm browserForms;

    @Autowired
    private BrowserWait browserWaits;

    @Autowired
    private BrowserNavigation browserNavigations;

    @Autowired BrowserManager browserManager;

    public void as(String player, String password) {
        browserWaits.waitForElement(USERNAME_FIELD);
        browserForms.fillTextField(USERNAME_FIELD,player);
        browserForms.fillTextField(PASSWORD_FIELD,password);
        browserForms.submitForm();
    }

    public void navigateToForgotPassword() {
        browserManager.driver().findElement(OVERLAY_HEADER).click();
        browserNavigations.navigateWithElement(FORGOT_PASSWORD_LINK);
    }

}
