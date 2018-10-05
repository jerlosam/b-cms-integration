package io.crazy88.beatrix.e2e.cashier;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.bonus.dto.LoginRequest;
import io.crazy88.beatrix.e2e.bonus.dto.LoginResponse;
import io.crazy88.beatrix.e2e.cashier.actions.DepositActions;
import io.crazy88.beatrix.e2e.clients.DepositClient;
import io.crazy88.beatrix.e2e.clients.LoginClient;
import io.crazy88.beatrix.e2e.clients.ProfileClient;
import io.crazy88.beatrix.e2e.clients.dto.DepositCreationRequest;
import io.crazy88.beatrix.e2e.clients.dto.DepositCreationResponse;
import io.crazy88.beatrix.e2e.clients.dto.PaymentEntrySource;
import io.crazy88.beatrix.e2e.player.JoinSteps;
import io.crazy88.beatrix.e2e.player.actions.PlayerAccountMenuActions;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import io.crazy88.beatrix.e2e.player.clients.SiteConfigClient;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

@Profile({"local", "test"})
@Component
public class DepositSteps {

    public static final String PAYMENT_CONTEXT = "paymentContext";

    private final String INSTRUMENT_TYPE = "BC";

    private final String CHANNEL_CODE = "W";

    private final String PAYMENT_TYPE = "D";

    private final String ECOMM_ORIGIN = "cashier30";

    public static final String PINCODE_FIELD_NAME = "fourDigitPinCode";

    @Autowired
    private DepositActions depositActions;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Autowired
    private PlayerAccountMenuActions playerAccountMenuActions;

    @Autowired
    private Reporter reporter;

    @Autowired
    private ProfileClient profileClient;

    @Autowired
    private DepositClient depositClient;

    @Autowired
    private LoginClient loginClient;

    @Autowired
    private E2EProperties e2EProperties;

    @Autowired
    private SiteConfigClient siteConfigClient;


    @Given("the existing player has made a deposit")
    @ToContext(PAYMENT_CONTEXT)
    public DepositCreationResponse playerMadeADeposit(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {

        DepositCreationRequest bitcoinDepositCreationRequest = generateDepositRequest(playerSignup.getEmail());

        LoginResponse loginResponse = doLogin(playerSignup);

        String authorization = "Bearer " + loginResponse.getSessionId();
        String transactionId = UUID.randomUUID().toString();
        DepositCreationResponse
                depositCreationResponse = depositClient.bitcoinDeposit(transactionId, authorization, ECOMM_ORIGIN,
                bitcoinDepositCreationRequest);

        reporter.info(String.format("Payment created with id: %s", depositCreationResponse.getId()));
        return depositCreationResponse;
    }

    @When("the player navigates to deposit page")
    public void navigateToDepositPage(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        playerNavigationActions.enterSite();
        playerNavigationActions.logIn(playerSignup.getEmail(), playerSignup.getPassword());
        if (!isPinCodeRequiredInSignUp() ) {
            playerAccountMenuActions.createPinCode();
        }
    	depositActions.navigateToDepositPage();
    }

    @When("the player create a pinCode")
    public void createPinCode(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        if (StringUtils.isEmpty(playerSignup.getPinCode())) {
            playerAccountMenuActions.createPinCode();
        } else {
            reporter.info("Skipping step, user has pin code");
        }
    }

    @Then("the player can see the deposit page")
    public void thenThePlayersCanSeeTheDepositPage() {
        AssertionsHelper.retryUntilSuccessful(15, () -> {
        	assertThat(depositActions.isDepositDisplayed()).isTrue();
        });
    }

    private DepositCreationRequest generateDepositRequest(String email) {
        UUID profileId = profileClient.search(e2EProperties.getBrandCode(), email).stream().findFirst().get().getProfileId();
        return getDepositRequest(profileId);
    }

    private LoginResponse doLogin(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        return loginClient.login(LoginRequest.builder()
                .username(playerSignup.getEmail())
                .password(playerSignup.getPassword())
                .build());
    }

    private DepositCreationRequest getDepositRequest(UUID accountNumber) {
        PaymentEntrySource entrySource = PaymentEntrySource.builder()
                .channelCode(CHANNEL_CODE)
                .ipAddress("")
                .build();

        return DepositCreationRequest.builder()
                .accountNumber(accountNumber.toString())
                .entrySource(entrySource)
                .paymentMethod(INSTRUMENT_TYPE)
                .paymentType(PAYMENT_TYPE)
                .instrumentTypeCode(INSTRUMENT_TYPE)
                .build();
    }

    private boolean isPinCodeRequiredInSignUp() {
        return getSignupFields()
                .stream()
                .anyMatch(field -> PINCODE_FIELD_NAME.equals(field));
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