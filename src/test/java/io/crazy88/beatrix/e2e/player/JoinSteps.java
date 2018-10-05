package io.crazy88.beatrix.e2e.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Profile;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.cashier.actions.DepositActions;
import io.crazy88.beatrix.e2e.clients.BrandPropertiesClient;
import io.crazy88.beatrix.e2e.clients.ProfileClient;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerSetupActions;
import io.crazy88.beatrix.e2e.player.actions.JoinActions;
import io.crazy88.beatrix.e2e.player.actions.JoinTestActions;
import io.crazy88.beatrix.e2e.player.actions.PlayerAccountMenuActions;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import io.crazy88.beatrix.e2e.player.actions.ReferAFriendActions;
import io.crazy88.beatrix.e2e.player.clients.SiteConfigClient;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import io.crazy88.beatrix.e2e.tools.RAFEnable;

@Profile({ "local", "test" })
@TestComponent
public class JoinSteps {

    public static final String PLAYER_SIGNUP_CONTEXT = "playerSignupContext";

    public static final String BLACKMAMBA = "BLACKMAMBA";

    public static final String REFEREE_SIGNUP_CONTEXT = "refereeSignupContext";

    public static final String RAF_COPY_LINK_CONTEXT = "rafCopyLinkContext";

    public static final String PINCODE_FIELD_NAME = "fourDigitPinCode";

    @Autowired
    E2EProperties e2eProperties;

    @Autowired
    private Reporter reporter;

    @Autowired
    private SiteConfigClient siteConfigClient;

    @Autowired
    private BrandPropertiesClient brandPropertiesClient;

    @Autowired
    private JoinTestActions joinTestActions;

    @Autowired
    private JoinActions joinActions;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Autowired
    CustomerManagerSetupActions customerManagerSetupActions;

    @Autowired
    private DepositActions depositActions;

    @Autowired
    private PlayerAccountMenuActions playerAccountMenuActions;

    @Autowired
    private ReferAFriendActions rafActions;

    @Autowired
    private ProfileClient profileClient;

    @Autowired
    CustomerManagerActions customerManagerActions;

    @Given("an existing player")
    @ToContext(value = PLAYER_SIGNUP_CONTEXT, retentionLevel = ToContext.RetentionLevel.SCENARIO)
    public PlayerSignup givenAnExistingPlayer() {
        PlayerSignup player = joinTestActions.signupRandomPlayerWithoutWebNavigation();
        customerManagerSetupActions.markAsTestAccountByEndpoint(player.getEmail());
        return player;
    }

    @Given("referee existing player")
    @ToContext(value = REFEREE_SIGNUP_CONTEXT, retentionLevel = ToContext.RetentionLevel.SCENARIO)
    public PlayerSignup givenARefereeExistingPlayer(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup referrer) {
        String referrerEmail = referrer.getEmail();
        UUID referrerId = profileClient.search(e2eProperties.getBrandCode(), referrerEmail).stream().findFirst().get().getProfileId();
        String token = rafActions.getReferrerTrackingCode(referrerId);
        PlayerSignup referee = joinTestActions.signupRandomRefereePlayerWithoutWebNavigation(referrerEmail, token);
        customerManagerSetupActions.markAsTestAccountByEndpoint(referee.getEmail());
        return referee;
    }

    @Given("an existing player without pinCode")
    @ToContext(value = PLAYER_SIGNUP_CONTEXT, retentionLevel = ToContext.RetentionLevel.SCENARIO)
    public PlayerSignup givenAnExistingPlayerWithoutPinCode() {
        PlayerSignup player = joinTestActions.signupRandomPlayerWithoutPinWithoutWebNavigation();
        customerManagerSetupActions.markAsTestAccountByEndpoint(player.getEmail());
        return player;
    }

    @Given("two existing players with similar email (1 character different)")
    @ToContext(value = PLAYER_SIGNUP_CONTEXT, retentionLevel = ToContext.RetentionLevel.SCENARIO)
    public PlayerSignup givenTwoExistingPlayers() {
        String randomEmail = RandomStringUtils.randomAlphanumeric(30);
        PlayerSignup playerCrazy =
                joinTestActions.signupPlayerWithEmail(String.format("%s@4null.com", randomEmail.concat("a")));
        PlayerSignup playerSlots =
                joinTestActions.signupPlayerWithEmail(String.format("%s@4null.com", randomEmail.concat("b")));
        customerManagerSetupActions.markAsTestAccountByEndpoint(playerCrazy.getEmail());
        customerManagerSetupActions.markAsTestAccountByEndpoint(playerSlots.getEmail());
        return playerCrazy;
    }

    @Given("an existing player with $balance balance forward")
    @ToContext(value = PLAYER_SIGNUP_CONTEXT, retentionLevel = ToContext.RetentionLevel.STORY)
    public PlayerSignup givenAnExistingPlayerWithBalance(final Float balance) {
        if (e2eProperties.getEnvironment().equalsIgnoreCase("b2pb")) {
            throw new NotImplementedException("Not hardcoded user for Production yet");
        } else {

            PlayerSignup player = joinTestActions.signupRandomPlayerWithoutWebNavigation();
            joinTestActions.doManualAccountEntry(e2eProperties.getCurrency(), player, balance);
            return player;
        }
    }

    @Given("referee existing alice affiliate")
    @ToContext(value = REFEREE_SIGNUP_CONTEXT, retentionLevel = ToContext.RetentionLevel.SCENARIO)
    public PlayerSignup givenAnAliceAffiliate(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup referrer) {
        String referrerEmail = referrer.getEmail();
        UUID referrerId = profileClient.search(e2eProperties.getBrandCode(), referrerEmail).stream().findFirst().get().getProfileId();
        PlayerSignup player = joinTestActions.signupRandomPlayerWithoutWebNavigation();
        customerManagerSetupActions.markAsTestAccountByEndpoint(player.getEmail());
        rafActions.addRefereeAffiliateWithExternalProfileId(referrerId, e2eProperties.getReferrerAliceAffiliate(),
                e2eProperties.getBrandCode());

        customerManagerActions.loginBackoffice();

        return player;
    }

    @When("a new player signs up")
    @ToContext(value = PLAYER_SIGNUP_CONTEXT, retentionLevel = ToContext.RetentionLevel.STORY)
    public PlayerSignup whenANewPlayerSignsUp() {

        PlayerSignup playerSignup = PlayerSignup.generateRandomPlayerSignupData(e2eProperties.getCurrency());
        reporter.debug("Profile created to be signed-up: " + playerSignup.getEmail());
        playerNavigationActions.enterSite();
        playerNavigationActions.navigateToJoin();
        joinActions.signUp(playerSignup, getSignupFields());
        customerManagerSetupActions.markAsTestAccountByEndpoint(playerSignup.getEmail());
        return playerSignup;
    }

    @RAFEnable
    @When("the referee signs up")
    @ToContext(value = REFEREE_SIGNUP_CONTEXT, retentionLevel = ToContext.RetentionLevel.STORY)
    public PlayerSignup whenTheRefereeSignsUp(@FromContext(RAF_COPY_LINK_CONTEXT) String copyLink) {

        PlayerSignup playerSignup = PlayerSignup.generateRandomPlayerSignupData(e2eProperties.getCurrency());
        reporter.debug("Profile created to be signed-up: " + playerSignup.getEmail());
        playerNavigationActions.enterSite();
        playerNavigationActions.navigateToJoinThroughReferrer(copyLink);
        joinActions.signUp(playerSignup, getSignupFields());

        // This wait is needed to allow the system to have fresh data when the environment is slow
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            reporter.error(e.getMessage());
        }
        customerManagerSetupActions.markAsTestAccountByEndpoint(playerSignup.getEmail());

        return playerSignup;
    }

    @Then("player is redirected to homepage")
    public void thenIsRedirectedToHomepage(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        assertThat(catchThrowable(() -> playerNavigationActions.isPlayerInTheHomePage())).isNull();
    }

    @Then("the player is redirected to the correct page")
    public void thenIsRedirectedToCorrectPage() throws InterruptedException {
        if(!isPinCodeRequiredInSignUp()) {
            try {
                playerAccountMenuActions.waitAndCreatePinCode();
            } catch (TimeoutException exception) {
                reporter.info("Pin code was not required");
            }
        }

        String template = e2eProperties.getBrandTemplate();

        if (BLACKMAMBA.equals(template)) {
            AssertionsHelper.retryUntilSuccessful(15, () -> {
                assertThat(depositActions.isFirstTimeDepositDisplayed()).isTrue();
            });
        } else {
            assertThat(catchThrowable(() -> playerNavigationActions.isPlayerInTheHomePage())).isNull();
        }
    }

    private boolean isPinCodeRequiredInSignUp() {
        return getSignupFields()
                .stream()
                .anyMatch(field -> PINCODE_FIELD_NAME.equals(field));
    }

    @Then("player is logged in")
    public void thenIsLoggedIn(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        assertThat(playerNavigationActions.isLoggedIn()).isTrue();
    }

    private List<String> getSignupFields() {
        return siteConfigClient
                .getSignupConfig()
                .getSignupFormFields()
                .stream()
                .flatMap(field -> Arrays.stream(field.split(",")))
                .collect(Collectors.toList());
    }

}