package io.crazy88.beatrix.e2e.bonus;

import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.bonus.actions.BonusActions;
import io.crazy88.beatrix.e2e.bonus.dto.Bonus;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import io.crazy88.beatrix.e2e.player.actions.JoinTestActions;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class BonusSteps {

    public static final String BONUS_CONTEXT = "bonusContext";

    @Autowired
    private BonusTestActions bonusTestActions;

    @Autowired
    private BonusActions bonusActions;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Autowired
    private JoinTestActions joinTestActions;

    @Autowired
    private E2EProperties e2EProperties;

//    @When("a reward manager user creates a new bonus")
//    @ToContext(BONUS_CONTEXT)
//    public Bonus whenRewardManagerCreatesANewBonus() {
//        Bonus bonus = bonusActions.generateRandomBonusData();
//        String internalCode = bonusActions.createBonus(bonus, e2EProperties.getBrandName());
//        bonus.setInternalCode(internalCode);
//        return bonus;
//    }

    @When("a reward manager user creates a new draft")
    @ToContext(BONUS_CONTEXT)
    public Bonus whenRewardManagerCreatesANewDraft() {
        String currency = bonusTestActions.getCurrencyFromBrandProperties(e2EProperties.getBrandCode());
        Bonus bonusDraft = bonusActions.generateRandomBonusDataByCurrency(currency);
        String internalCode = bonusActions.createDraft(bonusDraft, e2EProperties.getBrandName(),currency);
        bonusDraft.setInternalCode(internalCode);
        return bonusDraft;
    }

    @When("the player claims the first available upfront bonus")
    public void whenThePlayerClaimsTheFirstUpfrontBonus(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        playerNavigationActions.enterSite();
        playerNavigationActions.logIn(playerSignup.getEmail(),playerSignup.getPassword());
        bonusActions.claimFirstUpfrontBonus();
    }

    @When("the player forfeits the first active bonus")
    public void whenPlayerForfeitsFirstActiveBonus(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        bonusActions.forfeitFirstActiveBonus();
    }

    @When("a reward manager user delete the bonus")
    public void whenRewardManagerDeleteTheBonus(@FromContext(BONUS_CONTEXT) Bonus bonus) {
        bonusActions.deleteBonus(bonus);
    }

    @Then("the bonus is displayed in the bonus list")
    public void thenTheBonusIsDisplayed(@FromContext(BONUS_CONTEXT) Bonus bonus) {
        AssertionsHelper.retryUntilSuccessful(()-> {
            assertThat(bonusActions.isBonusDisplayedOnTheDashboard(bonus)).isTrue();
        });
    }

    @Then("the draft is displayed in the draft list")
    public void thenTheDraftIsDisplayed(@FromContext(BONUS_CONTEXT) Bonus bonusDraft) {
        AssertionsHelper.retryUntilSuccessful(()-> {
            assertThat(bonusActions.isDraftDisplayedOnTheDashboard(bonusDraft)).isTrue();
        });
    }

    @Then("the bonus is available for a new player")
    public void thenTheBonusIsAvailableForANewPlayer(@FromContext(BONUS_CONTEXT) Bonus bonus){
        PlayerSignup playerSignup = joinTestActions.signupRandomPlayerWithoutWebNavigation();
        bonusTestActions.isBonusAvailableForPlayer(bonus, e2EProperties.getBrandCode(), playerSignup.getEmail());

    }

    @Then("the bonus is not displayed in the bonus list")
    public void thenTheBonusIsDeleted(@FromContext(BONUS_CONTEXT) Bonus bonus) {
        assertThat(bonusActions.isBonusDisplayedOnTheDashboard(bonus)).isFalse();
    }

    @When("a reward manager user converts the draft into a new bonus")
    public Bonus whenRewardManagerConvertsDraftIntoANewBonus(@FromContext(BONUS_CONTEXT) Bonus bonusDraft) {
        bonusActions.editDraftAndConvertToBonus(bonusDraft);
        return bonusDraft;
    }
    @When("a reward manager user edits the bonus")
    public Bonus whenRewardManagerEditsBonus(@FromContext(BONUS_CONTEXT) Bonus bonus) {
        bonusActions.editBonus(bonus);
        return bonus;
    }
}