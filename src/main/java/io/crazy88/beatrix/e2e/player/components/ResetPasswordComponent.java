package io.crazy88.beatrix.e2e.player.components;

import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResetPasswordComponent {

    public static final By OVERLAY_HEADER = By.cssSelector("bx-overlay-header header");

    public static final By OVERLAY_BODY = By.cssSelector("bx-overlay-body section");

    public static final By OVERLAY = By.tagName("bx-overlay-container");

    private static final By PASSWORD_FIELD = By.cssSelector(".reset-password #password");

    @Autowired
    private BrowserForm browserForm;

    @Autowired
    private BrowserWait browserWait;

    public void resetPassword(String password) {
        browserWait.waitForElement(ResetPasswordComponent.PASSWORD_FIELD);
        browserForm.fillTextField(PASSWORD_FIELD, password);
        browserForm.submitForm();
    }
}
