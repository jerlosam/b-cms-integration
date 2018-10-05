package io.crazy88.beatrix.e2e.rewardmanager.actions;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.common.internal.components.InternalLoginComponent;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RewardManagerActions {

    private static final By LOYALTY_TAB = By.cssSelector("bx-navigation a.nav-link[href=\"/loyalty\"]");

    @Autowired
    private E2EProperties properties;

    @Autowired
    private BrowserNavigation browserNavigation;

    @Autowired
    private InternalLoginComponent internalLogin;

    @Autowired
    private Reporter reporter;

    public void enterToRewardsManagerHomePage() {
        enterToRewardsManagerHomePage(properties.getQaLdapUser(), properties.getQaLdapPassword());
    }

    public void enterToRewardsManagerLoyaltySection() {
        enterToRewardsManagerHomePage(properties.getQaLoyaltyLdapUser(), properties.getQaLoyaltyLdapPassword());
        reporter.info("Login successful with a Loyalty Enabled user");
        browserNavigation.navigateWithElement(LOYALTY_TAB);
        reporter.info("Rewards Manager user successfully navigated to LOYALTY section");
    }

    private void enterToRewardsManagerHomePage(String user, String pass) {
        browserNavigation.navigateToUrlWithRedirection(properties.getRewardManagerHomeUrl(),
                "login?returnUrl=%2Fbonuses%2Fnew");
        internalLogin.as(user, pass);
    }

}
