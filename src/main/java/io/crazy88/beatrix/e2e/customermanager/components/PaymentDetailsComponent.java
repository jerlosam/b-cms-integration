package io.crazy88.beatrix.e2e.customermanager.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.customermanager.dto.PaymentDetails;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentDetailsComponent {

    private static final String PAYMENT_DETAILS_OVERLAY_TAG = "bx-payment-details-overlay";

    private static final By PAYMENT_DETAILS_OVERLAY = By.tagName(PAYMENT_DETAILS_OVERLAY_TAG);

    private static final String ACCOUNT_NUMBER_LABEL = "Account Number:";
    private static final By ACCOUNT_NUMBER = By.xpath(getValueForLabel(ACCOUNT_NUMBER_LABEL));

    private static final String PAYMENT_TYPE_LABEL = "Payment Type:";
    private static final By PAYMENT_TYPE = By.xpath(getValueForLabel(PAYMENT_TYPE_LABEL));

    private static final String INSTRUMENT_TYPE_LABEL = "Instrument Type:";
    private static final By INSTRUMENT_TYPE = By.xpath(getValueForLabel(INSTRUMENT_TYPE_LABEL));

    private static final String AMOUNT_LABEL = "Amount:";
    private static final By AMOUNT = By.xpath(getValueForLabel(AMOUNT_LABEL));

    private static final By PAYMENT_TITLE = By.xpath("//" + PAYMENT_DETAILS_OVERLAY_TAG + "//header[contains(@class,'modal-header')]//span[2]");

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;

    private static String getValueForLabel(String label) {
        return "//" + PAYMENT_DETAILS_OVERLAY_TAG + "//table//tr[td[contains(text(), '" + label + "')]]//td[2]";
    }

    public PaymentDetails getPaymentDisplayed(){
        browserWait.waitForElement(PAYMENT_DETAILS_OVERLAY);
        browserWait.waitForElement(ACCOUNT_NUMBER);
        return PaymentDetails.builder()
                .title(browser.driver().findElement(PAYMENT_TITLE).getText())
                .accountNumber(browser.driver().findElement(ACCOUNT_NUMBER).getText())
                .paymentType(browser.driver().findElement(PAYMENT_TYPE).getText())
                .instrumentType(browser.driver().findElement(INSTRUMENT_TYPE).getText())
                .amount(browser.driver().findElement(AMOUNT).getText())
                .build();
    }

}
