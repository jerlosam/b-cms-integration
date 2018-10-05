package io.crazy88.beatrix.e2e.player.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerTransactionsComponent {

    private static final By TRANSACTION_LIST = By.className("bx-transaction-list-wrapper");
    private static final By BONUS_ICON = By.className("icon-bonus");
    private static final By CASINO_ICON = By.className("icon-playtime-chips");
    private static final By DEPOSIT_ICON = By.className("icon-deposit");
    private static final By WITHDRAWAL_ICON = By.className("icon-withdrawal");

    public enum TRANSACTION_TYPE  {BONUS, CASINO, DEPOSIT, WITHDRAWAL};

    @Autowired
    BrowserManager browser;

    @Autowired
    BrowserWait wait;

    public int getNumberOfTransactionsDisplayed(TRANSACTION_TYPE type) {
        wait.waitForElement(TRANSACTION_LIST);
        wait.waitForAngular();
        switch (type){
            case BONUS: return transactionsDisplayed(BONUS_ICON);
            case CASINO: return transactionsDisplayed(CASINO_ICON);
            case DEPOSIT: return transactionsDisplayed(DEPOSIT_ICON);
            case WITHDRAWAL: return transactionsDisplayed(WITHDRAWAL_ICON);
        }
        throw new IllegalStateException("Transaction type not supported");
    }

    private int transactionsDisplayed(By locator){
        return browser.driver().findElement(TRANSACTION_LIST).findElements(locator).size();
    }

    public boolean isPlayerInTransactionsScreen(){
        return (!browser.driver().findElements(TRANSACTION_LIST).isEmpty());
    }


}
