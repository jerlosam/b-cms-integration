package io.crazy88.beatrix.e2e.customermanager;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static io.crazy88.beatrix.e2e.player.JoinSteps.REFEREE_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

import io.crazy88.beatrix.e2e.AssertionsHelper;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;

@TestComponent
public class RelatedAccountsSteps {

    @Autowired
    private CustomerManagerActions customerManagerActions;

    @When("a customer manager navigates to related accounts")
    public void theUserNavigatesToRelatedAccounts(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        customerManagerActions.searchByEmailOnCustomerManager(playerSignup.getEmail());
        customerManagerActions.navigateToRelatedAccounts();
    }

    @Then("the relationship between referrer and referee is created")
    public void thenThePlayerRewardExclusionsAreDisplayed(@FromContext(REFEREE_SIGNUP_CONTEXT) PlayerSignup refereeSignup) {
        boolean relationshipCreated = customerManagerActions.isRelatedAccountRafRelationDisplayed(refereeSignup.getEmail());
        assertThat(relationshipCreated).isTrue();
    }

    @Then("the relationship between affiliate and referee is created")
    public void thenAffiliateRelationShipIsCreated(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup){
        AssertionsHelper.retryUntilSuccessful(() -> {
            boolean relationshipCreated = customerManagerActions.isAffiliateRelationDisplayed();
            assertThat(relationshipCreated).isTrue();
        });
    }

    @Then("affiliate is displayed in backoffice after clicking in relationship")
    public void thenAffiliateDisplayedInBackoffice(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup){
        boolean aliceAffiliateDisplayed = customerManagerActions.isCompanyAccountDisplayedInBackoffice();
        assertThat(aliceAffiliateDisplayed).isTrue();
    }


}
