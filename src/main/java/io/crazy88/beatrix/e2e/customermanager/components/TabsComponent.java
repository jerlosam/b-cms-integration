package io.crazy88.beatrix.e2e.customermanager.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class TabsComponent {

    private static final String REWARDS = "/rewards";

    private static final By TRANSACTIONS_TAB = By.xpath("//bx-navigation//a[text()=\"transactions\"]");

    private static final By ACTIVITY_TAB = By.xpath("//bx-navigation//a[text()=\"activity\"]");

    private static final By RELATED_ACCOUNTS_TAB = By.xpath("//bx-navigation//a[text()=\"Related Accounts\"]");

    private static final By REWARDS_TAB = By.xpath("//bx-navigation//a[text()=\"Rewards\"]");

    @Autowired
    BrowserManager browserManager;

    @Autowired
    BrowserWait browserWait;

    public void navigateToTransactionsTab(){
        browserManager.driver().findElement(TRANSACTIONS_TAB).click();
    }
    public void navigateToRelatedAccountsTab() {
        browserWait.waitForElement(RELATED_ACCOUNTS_TAB);
        browserManager.driver().findElement(RELATED_ACCOUNTS_TAB).click();
    }

    public void navigateToRewardsTab() {
        browserWait.waitForElement(REWARDS_TAB);

        browserManager.driver().findElement(REWARDS_TAB).click();
        browserWait.waitForUrlContaining(REWARDS);
    }

    public void navigateToActivityTab() {
        browserManager.driver().findElement(ACTIVITY_TAB).click();
    }
}
