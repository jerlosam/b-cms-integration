package io.crazy88.beatrix.e2e.player;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.clients.ProfileClient;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import io.crazy88.beatrix.e2e.player.actions.ReferAFriendActions;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import io.crazy88.beatrix.e2e.tools.RAFEnable;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static io.crazy88.beatrix.e2e.player.JoinSteps.REFEREE_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@Component
public class ReferAFriendSteps {

    public static final String RAF_COPY_LINK_CONTEXT = "rafCopyLinkContext";

    @Autowired
    private ReferAFriendActions rafActions;

    @Autowired
    private Reporter reporter;

    @Autowired
    private ProfileClient profileClient;

    @Autowired
    private E2EProperties e2EProperties;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @RAFEnable
    @When("the player navigates to refer page")
    public void whenThePlayerOpensReferPageAsLogged() {
        rafActions.navigateToReferPage();
    }

    @RAFEnable
    @Then("the player can see the sharing links")
    public void thenThePlayerCanSeeTheSharingLinks() {
        AssertionsHelper.retryUntilSuccessful(15, () -> {
            assertThat(rafActions.isSharingLinksDisplayed()).isTrue();
            reporter.info(String.format("Sharing links displayed successfully"));
        });
    }

    @RAFEnable
    @Then("the player can see the RAF button")
    public void thenThePlayerCanSeeTheRAFButton() {
        AssertionsHelper.retryUntilSuccessful(15, () -> {
            assertThat(rafActions.isRAFButtonDisplayed()).isTrue();
            reporter.info(String.format("RAF button displayed successfully"));
        });
    }

    @RAFEnable
    @ToContext(RAF_COPY_LINK_CONTEXT)
    @Then("the player copy the referrer link")
    public String thenThePlayerCopyTheReferrerLink() {
        return rafActions.copyLink();
    }

    @RAFEnable
    @Then("the relationship is created")
    public void thenTheRelationShipIsCreated(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup,
                                             @FromContext(REFEREE_SIGNUP_CONTEXT) PlayerSignup refereeSignup) {
        UUID referrerId = profileClient.search(e2EProperties.getBrandCode(), playerSignup.getEmail()).stream().findFirst().get().getProfileId();
        UUID refereeId = profileClient.search(e2EProperties.getBrandCode(), refereeSignup.getEmail()).stream().findFirst().get().getProfileId();
        AssertionsHelper.retryUntilSuccessful(15, () -> {
            assertTrue(rafActions.getReferrerId(refereeId).isPresent());
            AssertionsHelper.retryUntilSuccessful(15, () -> {
                assertThat(rafActions.getReferrerId(refereeId).get()).isEqualTo(referrerId);
                reporter.info(String.format("The referral relationship was created successfully"));
            });
        });
    }

    @RAFEnable
    @When("the player login through RAF")
    public void whenTheRelationShipIsCreated(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup player) {
        playerNavigationActions.logInThroughReferAFriend(player.getEmail(), player.getPassword());
    }

}
