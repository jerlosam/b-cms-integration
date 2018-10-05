package io.crazy88.beatrix.e2e.player.components;

import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ForgotPasswordComponent {

    public static final By OVERLAY_HEADER = By.cssSelector("bx-overlay-header header");

    public static final By OVERLAY_BODY = By.cssSelector("bx-overlay-body section");

    public static final By OVERLAY = By.tagName("bx-overlay-container");

    private static final By EMAIL_FIELD = By.id("forgotPassword-email");

    @Autowired
    private BrowserForm browserForms;

    @Autowired
    private BrowserWait browserWaits;

    public void requestResetPasswordLink(String email) {
        browserWaits.waitForElement(EMAIL_FIELD);
        browserForms.fillTextField(EMAIL_FIELD, email);
        browserForms.submitForm();
    }

}
