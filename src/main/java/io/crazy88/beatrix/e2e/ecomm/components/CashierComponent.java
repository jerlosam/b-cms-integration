package io.crazy88.beatrix.e2e.ecomm.components;

import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CashierComponent {

    private static final By CREDIT_CARD_BUTTON = By.id("CCLink");

    private static final By BITCOIN_BUTTON = By.id("BCLink");

    @Autowired
    private BrowserNavigation browserNavigations;

    public void navigateToCreditCard() {
        browserNavigations.navigateWithElement(CREDIT_CARD_BUTTON, false);
    }

    public void navigateToBitcoin() {
        browserNavigations.navigateWithElement(BITCOIN_BUTTON, false);
    }
}
