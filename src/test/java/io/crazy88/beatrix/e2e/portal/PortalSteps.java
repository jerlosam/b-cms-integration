package io.crazy88.beatrix.e2e.portal;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.common.internal.actions.InternalAppActions;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;
import io.crazy88.beatrix.e2e.rewardmanager.actions.RewardManagerActions;
import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class PortalSteps {

    private static final String INITIAL_URL_CONTEXT = "initialUrlContext";

    private static final String CUSTOMER_MANAGER = "CUSTOMER";

    @Autowired
    private PortalActions portalActions;

    @Autowired
    private CustomerManagerActions customerManagerActions;

    @Autowired
    private RewardManagerActions rewardManagerActions;

    @Autowired
    private InternalAppActions internalAppActions;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private Reporter reporter;

    @Given("a user logged in Portal")
    @ToContext(INITIAL_URL_CONTEXT)
    public String userLoggedInPortal() {
        portalActions.enterToPortalHomePage();

        boolean appSelectorPresent = portalActions.checkAppSelectorPresent();
        assertThat(appSelectorPresent).isTrue();

        return browser.driver().getCurrentUrl();
    }

    @Given("a user logged in Rewards Manager")
    @ToContext(INITIAL_URL_CONTEXT)
    public String userLoggedInRewardsManager() {
        rewardManagerActions.enterToRewardsManagerHomePage();
        return browser.driver().getCurrentUrl();
    }

    @Given("a user logged in Customer Manager")
    @ToContext(INITIAL_URL_CONTEXT)
    public String userLoggedInCustomerManager() {
        customerManagerActions.enterToCustomerManagerHomePage();
        return browser.driver().getCurrentUrl();
    }

    @Given("Customer Manager is available")
    public void customerManagerIsAvailable() {
        String text = portalActions.getAppSelectorTextInApp();
        assertThat(text).contains(CUSTOMER_MANAGER);
    }

    @When("the user navigates to the portal")
    public void navigateToThePortalHomepage() {
        portalActions.enterToPortalLoginPage();
    }

    @When("the user navigates to one of the available apps")
    public void navigatesToOneOfAvailableApps() {
        portalActions.navigateToOneOfAvailableApps();
    }

    @When("the user switches the app to Customer Manager")
    public void switchesToCustomerManager() {
        internalAppActions.switchToApp(CUSTOMER_MANAGER);
    }

    @When("the user does logout")
    public void userDoesLogout(){
        internalAppActions.logout();
    }

    @Then("the user is on Portal login page")
    public void userIsOnPortalLoginPage(){
        boolean onPortalLogin = portalActions.isOnPortalLoginPage();
        assertThat(onPortalLogin).isTrue();
    }

    @Then("the user is redirected to another app")
    public void userRedirectedToAnotherApp(@FromContext(INITIAL_URL_CONTEXT) String initialUrl) {
        String currentUrl = browser.driver().getCurrentUrl();
        reporter.info("Current URL: " + currentUrl);
        reporter.info("Previous URL: " + initialUrl);

        boolean prefixesMatch = initialUrl.substring(0, 10).equalsIgnoreCase(
                currentUrl.substring(0, 10));

        assertThat(prefixesMatch).isFalse();
    }

}
