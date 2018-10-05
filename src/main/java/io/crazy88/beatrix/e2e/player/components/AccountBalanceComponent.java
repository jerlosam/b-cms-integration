package io.crazy88.beatrix.e2e.player.components;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AccountBalanceComponent {

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserWait browserWaits;

    public static final By BONUS_FUNDS_BALANCE = By.className("bonus-balance");

    public BigDecimal getBonusBalanceAmount(){
        browserWaits.waitUntil(isPresent -> browser.driver().findElement(BONUS_FUNDS_BALANCE));
        String[] bonusFundsTextWords = browser.driver().findElement(BONUS_FUNDS_BALANCE).getText().toString().split(" ");
        int amountContainer = bonusFundsTextWords.length - 1;

        return new BigDecimal(bonusFundsTextWords[amountContainer]);
    }

    public boolean hasBonusFunds() {
        try {
            return browser.driver().findElement(BONUS_FUNDS_BALANCE).isDisplayed();
        }catch (Exception ex) {
            return false;
        }
    }

}
