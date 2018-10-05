package io.crazy88.beatrix.e2e.payoutservice;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import io.crazy88.beatrix.e2e.clients.InstrumentsClient;
import io.crazy88.beatrix.e2e.payoutservice.dto.Instrument;
import org.apache.commons.lang3.RandomStringUtils;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Profile;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.clients.PayoutServiceClient;
import io.crazy88.beatrix.e2e.clients.ProfileClient;
import io.crazy88.beatrix.e2e.payoutservice.actions.PayoutServiceActions;
import io.crazy88.beatrix.e2e.payoutservice.components.PayoutConsoleComponent;
import io.crazy88.beatrix.e2e.payoutservice.components.PayoutDetailsComponent;
import io.crazy88.beatrix.e2e.payoutservice.dto.PayoutRequest;
import io.crazy88.beatrix.e2e.player.actions.JoinTestActions;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import lombok.AllArgsConstructor;

@Profile({"!production"})
@TestComponent
@AllArgsConstructor
public class PayoutServiceSteps {

    public PayoutServiceClient client;
    public PayoutServiceActions payoutActions;
    public PayoutConsoleComponent consoleComponent;
    public PayoutDetailsComponent payoutDetailsComponent;
    public JoinTestActions joinTestActions;
    private ProfileClient profileClient;
    private InstrumentsClient instrumentsClient;
    private E2EProperties e2EProperties;
    BrowserManager browserManager;
    BrowserWait browserWait;

    private static final String PAYOUT_SERVICE_CONTEXT = "payoutServiceContext";
    private static final String PLAYER_INSTRUMENT_CONTEXT = "playerInstrumentContext";

    @Given("an existing payout request for $amount in the payout service")
    @ToContext(value = PAYOUT_SERVICE_CONTEXT, retentionLevel = ToContext.RetentionLevel.SCENARIO)
    public PayoutRequest requestPayoutServicePayout(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup,
                                                    Float amount,
                                                    @FromContext(PLAYER_INSTRUMENT_CONTEXT) Instrument instrument) {
        joinTestActions.doManualAccountEntry("USD", playerSignup, amount);
        UUID profileId = profileClient.search(e2EProperties.getBrandCode(), playerSignup.getEmail()).stream().findFirst().get().getProfileId();
        PayoutRequest request = PayoutRequest.builder()
                .amountRequested(String.format("%.2f", amount))
                .paymentMethodRequested("BC")
                .profileId(profileId.toString())
                .instrumentId(instrument.getId())
                .build();
        return client.requestPayout("W", request);
    }

    @Given("an existing withdrawal instrument")
    @ToContext(value = PLAYER_INSTRUMENT_CONTEXT, retentionLevel = ToContext.RetentionLevel.SCENARIO)
    public Instrument givenExistingWithdrawalInstrument(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        UUID profileId = profileClient.search(e2EProperties.getBrandCode(), playerSignup.getEmail()).stream().findFirst().get().getProfileId();
        return instrumentsClient.create(profileId.toString(),
                Instrument
                        .builder()
                        .profileID(profileId.toString())
                        .instrumentTypeCode("BC")
                        .instrumentNumber(generateBitcoinAddress())
                        .build(),
                "W");
    }

    private String generateBitcoinAddress () {
        return "bitcointaddress" + Instant.now().toEpochMilli();
    }

    @When("the user navigates to the Payout Console Page")
    public void navigateToPayoutConsole() {
        payoutActions.navigateToPayoutsConsolePage();
    }

    @When("the user navigates to the Payout Console Page with query string $queryString")
    public void navigateToPayoutConsoleWithQueryString(String queryString) {
        payoutActions.navigateToPayoutsConsolePageWithQueryString(queryString);
    }

    @When("the user navigates to the Payout Details page")
    public void playerClicksOnPayoutID(@FromContext(PAYOUT_SERVICE_CONTEXT) PayoutRequest payoutRequest) {
        payoutActions.navigateToPayoutsConsolePageWithQueryString("id=" + payoutRequest.getId());
        payoutActions.navigateToPayoutDetailsPageByPayoutIdLink(payoutRequest.getId());
    }

    @Then("the Payout Details page is displayed")
    public void thePayoutDetailsPageIsDisplayed(@FromContext(PAYOUT_SERVICE_CONTEXT) PayoutRequest payoutRequest) {
        assertThat(payoutActions.isPayoutDetailsComponentDisplayed()).isTrue();
        assertThat(payoutDetailsComponent.getDisplayedPayoutId()).isEqualTo(payoutRequest.getId());
    }

    @When("the users signs in to the payouts service")
    public void logIn() {
        payoutActions.signInToPayoutsServices();
    }

    @Then("the user is redirected to the authentication page")
    public void userIsRedirectedToAuthenticationPage() {
        browserWait.waitForUrlContaining("auth");
    }

    @Then("the user is redirected to the Payout Console Page")
    public void userIsRedirectedToPayoutsConsole() {
        browserWait.waitForUrlContaining("payouts-console");
        assertThat(payoutActions.isPayoutConsoleDisplayed()).isTrue();
    }
}
