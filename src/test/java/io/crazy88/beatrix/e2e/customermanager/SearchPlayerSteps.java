package io.crazy88.beatrix.e2e.customermanager;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;

@TestComponent
public class SearchPlayerSteps {

    @Autowired
    private CustomerManagerActions customerManagerActions;

    @When("a customer manager searches a player by email")
    public void theUserSearch(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        customerManagerActions.searchByEmailOnCustomerManager(getEmailCommonPartForSearch(playerSignup.getEmail()));
    }

    @Then("the player information is displayed on the results")
    public void playerInformationIsDisplayedOnTheResults(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        boolean isPlayerPresent = customerManagerActions.isPresentOnSearchResults(playerSignup.getEmail());
        assertThat(isPlayerPresent).isTrue().
                describedAs(String.format("Player with email %s is on the search results", playerSignup.getEmail()));
    }
    
    private String getEmailCommonPartForSearch(String email) {
    	String firstPartEmail = email.split("@")[0];
    	return firstPartEmail.substring(0, firstPartEmail.length() - 1).concat("*");
    }
}
