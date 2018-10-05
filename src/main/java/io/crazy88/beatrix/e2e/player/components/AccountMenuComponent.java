package io.crazy88.beatrix.e2e.player.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class AccountMenuComponent {

    @Autowired
    private BrowserNavigation browserNavigations;

    @Autowired
    private BrowserManager browserManager;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BonusAvailableComponent bonusAvailable;

    @Autowired
    private PlayerTransactionsComponent playerTransactions;

    @Autowired
    private PlayerSettingsComponent playerSettings;

    public static final By OVERLAY_HEADER = By.cssSelector("bx-account-menu header");

    public static final By OVERLAY = By.cssSelector(".bx-account-wrapper");

    public static final By MODAL_CONTENT = By.cssSelector(".modal-content");

    private By LOGOUT_BUTTON = By.cssSelector(".account-logout-button");

    private By WITHDRAW_BUTTON = By.cssSelector("button.account-withdraw-button");

    private By DEPOSIT_BUTTON = By.cssSelector("button.account-deposit-button");

    //TEST
    public static final By ACCOUNT_MENU_NAV = By.cssSelector(".bx-account-menu-nav");

    public static final By ACCOUNT_SUB_MENU_NAV = By.cssSelector(".bx-account-menu-subcontent");

    private static final By TRANSACTIONS_MENU_BUTTON =
            By.cssSelector(".bx-account-menu-nav .account-status .custom-menu .icon-transactions");
    private static final By BONUSES_MENU_BUTTON =
            By.cssSelector(".bx-account-menu-nav .account-status .custom-menu .icon-bonus");
    private static final By MESSAGES_MENU_BUTTON =
            By.cssSelector(".bx-account-menu-nav .account-status .custom-menu .icon-messages");
    private static final By SETTINGS_MENU_BUTTON =
            By.cssSelector(".bx-account-menu-nav .account-status .custom-menu .icon-profile-settings");

    public void navigateToTransactions() {
        if(playerTransactions.isPlayerInTransactionsScreen()){
            browserManager.driver().navigate().refresh();
        }else {
            browserManager.driver().findElement(TRANSACTIONS_MENU_BUTTON).click();
        }
        browserWait.waitForAngular();
    }

    public void navigateToMessages() {
        if (playerTransactions.isPlayerInTransactionsScreen()) {
            browserManager.driver().navigate().refresh();
        } else {
            browserManager.driver().findElement(MESSAGES_MENU_BUTTON).click();
        }
        browserWait.waitForAngular();
    }

    public void navigateToSettings() {
        if (playerSettings.isDisplayed()) {
            browserManager.driver().navigate().refresh();
        } else {
            browserManager.driver().findElement(SETTINGS_MENU_BUTTON).click();
        }
        browserWait.waitForAngular();
    }

    public void navigateToBonuses() {
        if(bonusAvailable.isPlayerInBonusScreen()){
            //TODO: workound until KIDDO-3274 if fix (navigating away instead of refresh)
//            browserManager.driver().navigate().refresh();
            navigateToTransactions();
            browserManager.driver().findElement(BONUSES_MENU_BUTTON).click();
        }else {
            browserManager.driver().findElement(BONUSES_MENU_BUTTON).click();
        }
    }

    public void navigateToWithdraw() {
        browserManager.driver().findElement(WITHDRAW_BUTTON).click();
        browserWait.waitForAngular();
    }

    public void navigateToDeposit() {
        browserManager.driver().findElement(DEPOSIT_BUTTON).click();
        browserWait.waitForAngular();
    }

    public boolean isPlayerInAccountMenu(){
        return (!browserManager.driver().findElements(OVERLAY_HEADER).isEmpty());
    }
    public void logout() {
        browserNavigations.navigateWithElement(LOGOUT_BUTTON);
    }

}
