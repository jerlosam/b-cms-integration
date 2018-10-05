package io.crazy88.beatrix.e2e.payoutservice.actions;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.EcommProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.ecomm.components.EcommLoginComponent;
import io.crazy88.beatrix.e2e.payoutservice.components.PayoutConsoleComponent;
import io.crazy88.beatrix.e2e.payoutservice.components.PayoutDetailsComponent;
import lombok.AllArgsConstructor;

@Profile({"!production"})
@Component
@AllArgsConstructor
public class PayoutServiceActions {

    protected E2EProperties properties;
    protected BrowserManager browserManager;
    protected BrowserNavigation browserNavigation;
    protected BrowserWait browserWaits;
    protected PayoutConsoleComponent payoutConsoleComponent;
    protected PayoutDetailsComponent payoutDetailsComponent;
    protected EcommProperties ecommProperties;
    protected EcommLoginComponent ecommLogin;

    public void navigateToPayoutsConsolePage() {
        browserManager.driver().get(properties.getEcomm().getPayoutServiceConsoleUrl()
                                    + PayoutConsoleComponent.PAYOUT_CONSOLE_URL);
    }

    public void navigateToPayoutsConsolePageWithQueryString(String queryString) {
        browserManager.driver().get(properties.getEcomm().getPayoutServiceConsoleUrl()
                + PayoutConsoleComponent.PAYOUT_CONSOLE_URL + "/?" + queryString);
    }

    public void navigateToPayoutDetailsPageByPayoutIdLink(String payoutId) {
        browserNavigation.navigateWithElement(payoutConsoleComponent.getDetailsLinkByPayoutId(payoutId));
    }

    public void filterByID(String id) {
        payoutConsoleComponent.filterByID(id);
    }

    public boolean isPayoutDetailsComponentDisplayed() {
        return payoutDetailsComponent.isPayoutDetailsComponentDisplayed();
    }

    public boolean isPayoutConsoleDisplayed() {
        return payoutConsoleComponent.isPayoutConsoleDisplayed();
    }
    
    public void signInToPayoutsServices() {
        String user = ecommProperties.getPayoutsUser();
        String password = ecommProperties.getPayoutsPassword();

        ecommLogin.as(user, password);
    }
    
}
