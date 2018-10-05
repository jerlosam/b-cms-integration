package io.crazy88.beatrix.e2e.routing;

import static org.assertj.core.api.Assertions.assertThat;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.routing.actions.RoutingActions;

@TestComponent
public class RoutingSteps {

    @Autowired
    private RoutingActions routingActions;

    @When("a operations manager signs in to ecomm routing services")
    public void signInToEcommRoutingServices() {
        routingActions.signInToEcommRoutingServices();
    }

    @When("the operations manager navigates to payment method rules page")
    public void navigateToPaymentMethodRulesPage() {
        routingActions.navigateToPaymentMethodRulesPage();
    }

    @Then("the operations manager can see the payment method rules")
    public void thenTheOperarionsManagerCanSeeThePaymentMethodRules() {
        AssertionsHelper.retryUntilSuccessful(() -> assertThat(routingActions.isPaymentMethodRuleDisplayed()).isTrue());
    }

}
