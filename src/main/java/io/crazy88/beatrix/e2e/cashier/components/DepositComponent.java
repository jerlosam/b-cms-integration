package io.crazy88.beatrix.e2e.cashier.components;

import internal.katana.selenium.BrowserManager;
import internal.katana.selenium.core.browser.BrowserVendor;
import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DepositComponent {
	
	@Autowired
    BrowserManager browser;

	@Autowired BrowserValidation browserValidations;
	
    @Autowired BrowserWait browserWaits;
    
    private static final List<String> DEPOSIT_TITLE_KEYWORDS_ALLOWED = Arrays.asList("DEPOSIT", "CASHIER", "FUND", "BITCOIN", "CREDIT CARD");
    
    private static final By COMPONENT = By.xpath("//bx-deposit-bitcoin-channel|//bx-payment-method-selector");

    private static final By FIRST_TIME_DEPOSITOR_COMPONENT = By.xpath("//bx-payment-method-selector");

    public boolean isDepositDisplayed() {
        return browserValidations.isElementDisplayed(COMPONENT);
    }

    public boolean isFirstTimeDepositDisplayed() {
        return browserValidations.isElementDisplayed(FIRST_TIME_DEPOSITOR_COMPONENT);
    }
//
//    public boolean isTitleDisplayed() {
//        if(browser.driver().findElements(TITLE).size() != 1){
//            return false;
//        }
//        if(browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI){
//            return true;
//        }
//        String titleText = browser.driver().findElement(TITLE).getText().toUpperCase();
//        return DEPOSIT_TITLE_KEYWORDS_ALLOWED.parallelStream().anyMatch(titleText::contains);
//    }
//
//    public boolean isFirstTimeTitleDisplayed() {
//        if(browser.driver().findElements(FIRST_TIME_DEPOSITOR_TITLE).size() != 1){
//            return false;
//        }
//        if(browser.browserDriver().browser().getBrowserVendor() == BrowserVendor.SAFARI){
//            return true;
//        }
//        String titleText = browser.driver().findElement(FIRST_TIME_DEPOSITOR_TITLE).getText().toUpperCase();
//        return DEPOSIT_TITLE_KEYWORDS_ALLOWED.parallelStream().anyMatch(titleText::contains);
//    }
//
//    public boolean isPaymentMethodSelectorDisplayed() {
//        return browserValidations.isElementDisplayed(PAYMENT_METHOD_SELECTOR);
//    }
}
