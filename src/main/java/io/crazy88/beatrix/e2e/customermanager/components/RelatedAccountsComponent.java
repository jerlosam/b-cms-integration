package io.crazy88.beatrix.e2e.customermanager.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelatedAccountsComponent {

    private static final String REFERRED_BY_TABLE = ".referred-by tbody tr ";

    private static final String REFERRED_BY_TYPE_COL = ".referred-by-type-col";

    private static final String REFERRED_ACCOUNTS_TABLE = ".referred-accounts tbody tr ";

    private static final String REFERRED_ACCOUNTS_CHANNEL_COL = ".sharing-method-col";

    private static final String REFERRED_ACCOUNTS_EMAIL_COL = ".email-col";

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;


    public boolean isReferredByPlayerDisplayed(){
        browserWait.waitForAngular();
        final By referredByTypeSelector = By.cssSelector(REFERRED_BY_TABLE + REFERRED_BY_TYPE_COL);
        return browser.driver().findElement(referredByTypeSelector).getText().equalsIgnoreCase("Player");

    }

    public boolean isReferredByRafDisplayed(String email) {
        browserWait.waitForAngular();
        browserWait.waitForElement(By.cssSelector(REFERRED_ACCOUNTS_TABLE + REFERRED_ACCOUNTS_CHANNEL_COL));
        final By referredByTypeSelector = By.cssSelector(REFERRED_ACCOUNTS_TABLE + REFERRED_ACCOUNTS_CHANNEL_COL);
        final By referredByEmailSelector = By.cssSelector(REFERRED_ACCOUNTS_TABLE + REFERRED_ACCOUNTS_EMAIL_COL);
        return browser.driver().findElement(referredByTypeSelector).getText().equalsIgnoreCase("Copy")
                & browser.driver().findElement(referredByEmailSelector).getText().equalsIgnoreCase(email);

    }

    public boolean isReferredByAffiliateDisplayed(){
        browserWait.waitForAngular();
        final By referredByTypeSelector = By.cssSelector(REFERRED_BY_TABLE + REFERRED_BY_TYPE_COL);
        return browser.driver().findElement(referredByTypeSelector).getText().equalsIgnoreCase("Affiliate");

    }

    public void navigateToAliceProfile(){
        browserWait.waitForAngular();
        final By referredByTypeSelector = By.cssSelector(REFERRED_BY_TABLE + REFERRED_BY_TYPE_COL);
        browser.driver().findElement(referredByTypeSelector).click();

    }

}
