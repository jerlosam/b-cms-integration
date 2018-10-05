package io.crazy88.beatrix.e2e.player.components;

import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionsComponent {

    public static final By OVERLAY_HEADER = By.cssSelector("bx-overlay-header");

    @Autowired
    BrowserValidation browserValidations;

    public boolean isDisplayed() {
        return browserValidations.isElementDisplayed(OVERLAY_HEADER);
    }

}
