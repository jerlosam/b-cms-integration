package io.crazy88.beatrix.e2e.routing.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.EcommProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.ecomm.components.EcommLoginComponent;
import io.crazy88.beatrix.e2e.routing.components.RoutingComponent;

@Component
@Profile("!local")
public class RoutingActions {

    private static final String PAGE_PAYMENT_METHOD_RULES = "/rules";

    @Autowired
    BrowserNavigation browserNavigation;

    @Autowired
    EcommProperties ecommProperties;

    @Autowired
    private RoutingComponent routingComponent;

    @Autowired
    private EcommLoginComponent routingLogin;

    public void signInToEcommRoutingServices() {
        String user = ecommProperties.getRoutingUser();
        String password = ecommProperties.getRoutingPassword();
        String authUrl = ecommProperties.getRoutingAuthUrl();

        browserNavigation.navigateToUrl(authUrl);
        routingLogin.as(user, password);
    }

    public void navigateToPaymentMethodRulesPage() {
        browserNavigation.navigateToUrl(ecommProperties.getRoutingUrl() + PAGE_PAYMENT_METHOD_RULES);
    }

    public boolean isPaymentMethodRuleDisplayed() {
        return routingComponent.isPaymentMethodRuleDisplayed();
    }

}
