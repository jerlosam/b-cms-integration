package io.crazy88.beatrix.e2e.payoutservice.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class PayoutConsoleComponent {

    private static final By ID_FILTER = By.cssSelector("input#filter-id");
    private static final By ID_FILTER_SORT = By.cssSelector("#idAsc + label");
    public static final By PAYOUT_CONSOLE = By.cssSelector("bx-payouts-list table tbody tr");
    public static final String PAYOUT_CONSOLE_URL = "payouts-console";

    @Autowired
    private BrowserValidation browserValidation;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browserManager;

    public By getDetailsLinkByPayoutId(String payoutId) {
        return By.cssSelector("a[href*='" + payoutId + "']");
    }

    private static By getXpathForID(String id) {
        return By.xpath("//table//tr//td//a[contains(text(), '" + id + "')]");
    }

    public void filterByID(String id) {
        browserManager.driver().findElement(ID_FILTER).sendKeys(id);
        browserManager.driver().findElement(ID_FILTER_SORT).click();
        browserWait.waitForElementPresent(getXpathForID(id));
    }

    public boolean isPayoutConsoleDisplayed() {
        return browserValidation.isElementDisplayedWaitFor(PAYOUT_CONSOLE, 10L);
    }
}
