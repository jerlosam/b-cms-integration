package io.crazy88.beatrix.e2e.routing.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.BrowserValidation;

@Component
public class RoutingComponent {

    private static final By PAYMENT_METHOD_RULE = By.cssSelector(".paymentMethodRule");

    private static final By PAYMENT_METHOD_GROUP = By.cssSelector(".paymentMethodGroup");

    @Autowired
    private BrowserValidation browserValidation;

    public boolean isPaymentMethodRuleDisplayed() {
        return browserValidation.isElementDisplayedInContainer(PAYMENT_METHOD_RULE, PAYMENT_METHOD_GROUP);
    }

}
