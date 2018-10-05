package io.crazy88.beatrix.e2e.player.components;

import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FooterComponent {

    @Autowired
    private BrowserNavigation browserNavigation;

    @Autowired
    private BrowserWait browserWait;

    private By JOIN_STICKY_CTA_BUTTON = By.cssSelector("bx-sticky-cta .primary");

    private By LOGIN_STICKY_CTA_BUTTON = By.cssSelector("bx-sticky-cta .tertiary");

    public void navigateToJoin() {
        browserNavigation.navigateWithElement(JOIN_STICKY_CTA_BUTTON);
        browserWait.waitForElement(SignupComponent.OVERLAY_HEADER);
    }

    public void navigateToLogin(){
        browserNavigation.navigateWithElement(LOGIN_STICKY_CTA_BUTTON);
        browserWait.waitForElement(LoginComponent.OVERLAY_HEADER);
    }
}
