package io.crazy88.beatrix.e2e.rewardmanager.loyalty.components;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoyaltyHeaderComponent {

    private static final By BRAND_SELECTOR = By.tagName("bx-brand-select");

    private static final By BRAND_SELECTOR_DIALOG = By.tagName("bx-dialog");

    private static final By BRAND_SELECTOR_CHANGE = By.cssSelector("button.primary");

    private static final By HEADER = By.cssSelector("bx-navigation");

    private static final By NEW_PROGRAM = By.cssSelector("bx-navigation a.nav-link[href=\"/loyalty/new-program\"]");

    private static final By EXISTING_PROGRAM = By.cssSelector("bx-navigation a.nav-link[href=\"/loyalty/existing-programs\"]");

    @Autowired
    private BrowserForm browserForm;

    @Autowired
    BrowserNavigation browserNavigations;

    @Autowired
    BrowserWait browserWait;

    @Autowired
    Reporter reporter;

    public void navigateToNewProgram() {
        browserNavigations.navigateWithElement(NEW_PROGRAM);
    }

    public void navigateToExistingPrograms() {
        browserNavigations.navigateWithElement(EXISTING_PROGRAM);
    }

    public boolean isDisplayed() {
        try {
            browserWait.waitForElement(HEADER);
            reporter.info("DOM element HEADER [" + HEADER.toString() + "] is present in the page as expected.");

            browserWait.waitForElement(NEW_PROGRAM);
            reporter.info("New Program CTA [" + NEW_PROGRAM.toString() + "] is present in the page as expected.");

            browserWait.waitForElement(EXISTING_PROGRAM);
            reporter.info("Existing Programs CTA [" + EXISTING_PROGRAM.toString() + "] is present in the page as expected.");

            return true;
        } catch (final TimeoutException e) {
            return false;
        }
    }

    public void selectBrand(String brand) {
        browserForm.selectValueOnSelector(BRAND_SELECTOR, brand);
        browserForm.clickOnElement(BRAND_SELECTOR_DIALOG, BRAND_SELECTOR_CHANGE);
    }
}
