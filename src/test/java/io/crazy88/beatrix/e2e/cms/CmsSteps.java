package io.crazy88.beatrix.e2e.cms;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.cms.actions.CmsActions;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.stereotype.Component;

@Component
public class CmsSteps {

    @Autowired
    private CmsActions cmsActions;

    @Autowired
    private BrowserNavigation browserNavigation;

    @Given("a page is created using CMS")
    public void createPageViaCMS() {
        cmsActions.enterCMSSite();
        cmsActions.createContentForSlotsLV();
    }

    @When("the tester created a Promotion Page")
    public void createPromotionsPage() {
        cmsActions.createPromotionsContent();
        cmsActions.createPromotionsMenu();
        cmsActions.createPromotionsLandingPage();
    }

    @Then("the page should display the correct fields")
    public void checkPageFields() {
        // cmsActions.goToSlotsPromotionsPage();
        // cmsActions.checkPageFields();

    }

}
