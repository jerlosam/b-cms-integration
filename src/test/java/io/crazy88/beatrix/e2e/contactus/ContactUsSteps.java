package io.crazy88.beatrix.e2e.contactus;

import static org.assertj.core.api.Assertions.assertThat;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.player.actions.ContactUsActions;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;

@Component
public class ContactUsSteps {

    @Autowired
    private ContactUsActions contactUsActions;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Given("the player navigates to contact us page")
    @When("the player navigates to contact us page")
    public void playerNavigatesToContactUs() {
        playerNavigationActions.enterSite();
        contactUsActions.navigateToContactUsPage();
    }

    @When("the player closes contact us page and navigates to join")
    public void playerClosesContactUsPageAndNavigatesToJoin(){
        contactUsActions.closeContactUs();
        playerNavigationActions.navigateToJoin();
    }

    @Then("the player can see the contact us page")
    public void playerCanSeeTheContactUsPage() {
        AssertionsHelper.retryUntilSuccessful(15,
                () -> assertThat(contactUsActions.isContactUsDisplayed()).isTrue());
    }
}