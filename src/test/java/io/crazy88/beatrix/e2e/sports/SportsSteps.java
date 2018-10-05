package io.crazy88.beatrix.e2e.sports;

import static org.assertj.core.api.Assertions.assertThat;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.sports.actions.SportsHomeActions;

@Component
public class SportsSteps {

    @Autowired
    private SportsHomeActions sportsHomeActions;

    /**
     * Navigates to Sports home page by URL (without signing in).
     * At the moment, this is the only possible way to navigate to it, until the site is finished.
     */
    @When("the player navigates to sports page")
    public void navigateToSportsPageUrl() {
        sportsHomeActions.navigateToSportsHomePageUrl();
    }

    @Then("sports home page is displayed")
    public void homePageDisplayed() {
        AssertionsHelper.retryUntilSuccessful(() -> assertThat(sportsHomeActions.isHomeComponentDisplayed()).isTrue());
    }

}
