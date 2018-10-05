package io.crazy88.beatrix.e2e.customermanager.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchHeaderComponent {

    private static final By TRANSACTION_FIELD = By.id("transaction-search-input");

    private static final By HEADER_SEARCH_BUTTON = By.xpath("//*[contains(name(),'bx-header')]//a[contains(text(), 'search')]");

    @Autowired
    private BrowserForm browserForms;

    @Autowired
    private BrowserManager browser;

    public void searchByPaymentId(String paymentId) {
        browserForms.fillTextField(TRANSACTION_FIELD, paymentId);
        browser.driver().findElement(HEADER_SEARCH_BUTTON).click();
    }

}
