package io.crazy88.beatrix.e2e.player.actions;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.player.components.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PlayerAccountMenuActions {

    @Autowired
    private AccountMenuComponent accountMenu;

    @Autowired
    private HeaderComponent header;

    @Autowired
    private PlayerTransactionsComponent transactions;

    @Autowired
    private AccountBalanceComponent balance;

    @Autowired
    private PinCodeComponent pinCode;

    @Autowired
    private PlayerNavigationActions playerNavigation;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserWait browserWait;

    public void navigateToAvailableBonuses(){
        navigateToAccountMenuIfNeeded();
        accountMenu.navigateToBonuses();
    }

    public void navigateToTransactions(){
        navigateToAccountMenuIfNeeded();
        accountMenu.navigateToTransactions();
    }

    public void navigateToMessages(){
        navigateToAccountMenuIfNeeded();
        accountMenu.navigateToMessages();
    }

    public void navigateToProfileSettings(){
        navigateToAccountMenuIfNeeded();
        accountMenu.navigateToSettings();
    }

    public int getNumberOfBonusStatements() {
        navigateToTransactions();
        return transactions.getNumberOfTransactionsDisplayed(PlayerTransactionsComponent.TRANSACTION_TYPE.BONUS);
    }

    public BigDecimal getPlayersWithdrawableBalance(){
        if(!accountMenu.isPlayerInAccountMenu()) {
            playerNavigation.navigateToHome();
            header.navigateToAccountMenu();
        }else {
            browser.driver().navigate().refresh();
            browserWait.waitForAngular();
        }
        return balance.getBonusBalanceAmount();
    }

    public void waitAndCreatePinCode() {
        pinCode.waitForPinCodeDisplayed();
        createPinCode();
    }

    public void createPinCode() {
        if ( pinCode.isPinCodeOverlayDisplayed() ) {
            pinCode.createPinCode();
            pinCode.waitForPinCodeNotDisplayed();
        }
    }

    public boolean checkIsPinCodeFormShown() {
        return pinCode.isPinCodeOverlayDisplayed();
    }

    public BigDecimal getPlayersPlayableBalance(){
        if(!accountMenu.isPlayerInAccountMenu()) {
            playerNavigation.navigateToHome();
            header.navigateToAccountMenu();
        }else {
            browser.driver().navigate().refresh();
            browserWait.waitForAngular();
        }
        return balance.getBonusBalanceAmount();
    }

    public boolean bonusFundsBalanceDoesNotDisplay() {
        return !balance.hasBonusFunds();
    }

    private void navigateToAccountMenuIfNeeded() {
        if(!accountMenu.isPlayerInAccountMenu()) {
            playerNavigation.navigateToHome();
            header.navigateToAccountMenu();
        }
    }
}
