package io.crazy88.beatrix.e2e.payoutservice.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.payoutservice.dto.PayoutRequest;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayoutDetailsComponent {

    private static final By PAYOUT_ID = By.cssSelector("li#payout-id span.value");
    private static final By PAYOUT_DETAILS_PAGE = By.className("payout-detail");

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserValidation browserValidation;

    @Autowired
    private BrowserManager browserManager;

    public boolean isPayoutDetailsComponentDisplayed() {
        return browserValidation.isElementDisplayed(PAYOUT_DETAILS_PAGE);
    }

    public String getDisplayedPayoutId() {
        return browserManager.driver().findElement(PAYOUT_ID).getText();
    }

}
