package io.crazy88.beatrix.e2e.customermanager;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.bonus.BonusTestActions;
import io.crazy88.beatrix.e2e.clients.dto.AvailableRewards;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;
import io.crazy88.beatrix.e2e.customermanager.dto.RewardExclusions;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;

@TestComponent
public class RewardSteps {

    private static final String REWARD_EXCLUSIONS_PREVIOUS = "rewardExclusionsPrevious";

    private static final String BONUS_NAME_TO_ADD = "bonusNameToAdd";

    private static final String BONUS_NAME_TO_REMOVE = "bonusNameToRemove";

    @Autowired
    private CustomerManagerActions customerManagerActions;

    @Autowired
    private BonusTestActions bonusTestActions;

    @Autowired
    private E2EProperties e2EProperties;

    @When("a customer manager navigates to the player reward exclusions page")
    public void theUserNavigatesToPlayerRewardExclusions(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        customerManagerActions.searchByEmailOnCustomerManager(playerSignup.getEmail());
        customerManagerActions.navigateToRewardExclusions();
    }

    @Then("the player reward exclusions are displayed")
    public void thenThePlayerRewardExclusionsAreDisplayed() {
        RewardExclusions rewardExclusions = customerManagerActions.getRewardExclusions();
        assertThat(rewardExclusions.getRewardExclusions()).isNotEmpty();

    }

    @When("a customer manager excludes all bonuses")
    @ToContext(REWARD_EXCLUSIONS_PREVIOUS)
    public AvailableRewards theUserChecksFirstDepositBonus(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        AvailableRewards availableRewards =
                bonusTestActions.getBonusAvailableForPlayer(e2EProperties.getBrandCode(), playerSignup.getEmail());

        customerManagerActions.excludeAllRewards();

        return availableRewards;
    }

    @Then("no bonuses are returned as available")
    public void thenNoFTDBonusIsReturnedAsAvailable(
            @FromContext(REWARD_EXCLUSIONS_PREVIOUS) AvailableRewards availableRewardsPrevious,
            @FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {

        AssertionsHelper.retryUntilSuccessful(()-> {
            AvailableRewards availableRewardsCurrent =
                    bonusTestActions.getBonusAvailableForPlayer(e2EProperties.getBrandCode(), playerSignup.getEmail());

            assertThat(availableRewardsCurrent.getAvailableRewards()).isEmpty();

            assertThat(availableRewardsCurrent.getAvailableRewards()).isSubsetOf(availableRewardsPrevious.getAvailableRewards());
        });
    }
    @When("a customer manager navigates to the player Target Lists page")
    public void theUserNavigatesToPlayerTargetLists(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        customerManagerActions.searchByEmailOnCustomerManager(playerSignup.getEmail());
        customerManagerActions.navigateToTargetLists();
    }

    @When("the customer manager adds the player as targeted of the first bonus")
    @ToContext(BONUS_NAME_TO_ADD)
    public String theUserAddsPlayerAsTargeted() {
        return customerManagerActions.clickOnAddButtonOfFirstBonus();
    }

    @Then("bonus is displayed in player lists")
    public void bonusIsDisplayedInPlayerLists(@FromContext(BONUS_NAME_TO_ADD) String expectedBonusCode) {
        assertThat(customerManagerActions.bonusDisplayedPlayerLists(expectedBonusCode)).isTrue();
    }

    @When("the customer manager removes the player as targeted of the first bonus")
    @ToContext(BONUS_NAME_TO_REMOVE)
    public String theUserRemovesPlayerAsTargeted() {
        return customerManagerActions.clickOnRemoveButtonOfFirstBonus();
    }

    @Then("bonus is displayed in available lists")
    public void bonusIsDisplayedInAvailableLists(@FromContext(BONUS_NAME_TO_REMOVE) String expectedBonusCode) {
        assertThat(customerManagerActions.bonusDisplayedAvailableLists(expectedBonusCode)).isTrue();
    }
}
