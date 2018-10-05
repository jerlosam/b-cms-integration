package io.crazy88.beatrix.e2e.customermanager.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionsComponent {

    private static final By TRANSACTIONS_LIST = By.xpath("//bx-transaction-list-cm//tbody");

    private static final By TRANSACTION_REF = By.cssSelector(".bx-transaction-ref");

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;


    public boolean isTransactionsWithReferenceDisplayed(String reference){
        browserWait.waitForElement(TRANSACTIONS_LIST);
        browserWait.waitForAngular();
        List<WebElement> transactions = transactionsDisplayed(TRANSACTION_REF);
        return transactions.stream().anyMatch(transaction -> transaction.getText().equals(reference));
    }

    private List<WebElement> transactionsDisplayed(By locator){
        return browser.driver().findElement(TRANSACTIONS_LIST).findElements(locator);
    }

}
