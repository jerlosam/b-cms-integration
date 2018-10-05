package io.crazy88.beatrix.e2e.customermanager;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Year;

import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerSetupActions;
import io.crazy88.beatrix.e2e.customermanager.dto.ManualAccountEntry;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerBalances;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerDetails;
import io.crazy88.beatrix.e2e.customermanager.dto.PlayerDetailsByAreas;
import io.crazy88.beatrix.e2e.player.WalletSteps;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;

@TestComponent
public class AccountDetailsSteps {

    public static final String PLAYER_DETAILS_CONTEXT = "playerDetails";
    public static final String MANUAL_ACCOUNT_ENTRY_CONTEXT = "manualAccountEntry";
    public static final String PLAYER_ACCOUNT_NUMBER_CONTEXT = "playerAccountNumber";

    @Autowired
    private CustomerManagerActions customerManagerActions;

    @Autowired
    private CustomerManagerSetupActions setupActions;

    @Autowired
    private BrowserManager browserManager;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserNavigation browserNavigation;

    @Autowired
    private Reporter reporter;

    @When("a customer manager navigates to the player account details")
    public void theUserNavigatesToPlayerAccountDetails(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        String oldUrl = browserManager.driver().getCurrentUrl();
        customerManagerActions.searchByEmailOnCustomerManager(playerSignup.getEmail());
        browserWait.waitForUrlChange(oldUrl);
        if (setupActions.searchEmail(playerSignup.getEmail()) > 1){
            customerManagerActions.navigateToPlayerFromSearchResults(playerSignup.getEmail());
        }
    }

    @Then("the player balance information is displayed on the page")
    public void thenBalanceInformationIsDisplayed(@FromContext(WalletSteps.KNOWN_BALANCE_CONTEXT) BigDecimal balance) {
    	PlayerBalances playerBalance = customerManagerActions.getDisplayedPlayerBalancesFromCustomerManager();
    	reporter.info(String.format("Player balances: %s", playerBalance.toString()));

    	assertThat(playerBalance.getTotalBalance()).isEqualByComparingTo(balance);
    	assertThat(playerBalance.getTotalCash()).isEqualByComparingTo(balance);
        assertThat(playerBalance.getDetailedBalances()).containsOnlyKeys("Locked Cash", "Withdrawable Cash", "Bonus");
    }

    @Then("the player is found in customer manager and the player balance displayed is greater than zero")
    public void thenBalanceIsHigherThanZero(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) throws ParseException {
        String oldUrl = browserManager.driver().getCurrentUrl();
        String playerEmail = playerSignup.getEmail();
        customerManagerActions.searchByEmailFromBulkChangeManagerInCustomerManager(playerEmail);
        browserWait.waitForUrlChange(oldUrl);
        if (setupActions.searchEmail(playerEmail) > 1){
            customerManagerActions.navigateToPlayerFromSearchResults(playerEmail);
        }
        thenBalanceIsHigherThanZero();
    }

    @Then("the player balance displayed in customer manager is greater than zero")
    public void thenBalanceIsHigherThanZero() throws ParseException {
        AssertionsHelper.retryUntilSuccessful(()-> {
            PlayerBalances balances = customerManagerActions.getDisplayedPlayerBalancesFromCustomerManager();
            assertThat(balances.getTotalBalance()).isGreaterThan(BigDecimal.ZERO);
        });
    }

    @When("a customer manager updates the player details")
    @ToContext(PLAYER_DETAILS_CONTEXT)
    public PlayerDetails whenPlayerDetailsAreUpdated(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup){
        theUserNavigatesToPlayerAccountDetails(playerSignup);
        return customerManagerActions.updatePlayerDetails();
    }

    @When("a customer manager performs a manual account entry")
    @ToContext(MANUAL_ACCOUNT_ENTRY_CONTEXT)
    public ManualAccountEntry whenACMPerformsAManualAccountEntry(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup){
        customerManagerActions.searchByEmailOnCustomerManager(playerSignup.getEmail());
        return customerManagerActions.performAManualAccountEntry();
    }

    @Then("the player details are updated")
    public void thenPlayerInfoIsDisplayedOnThePlayerDetails(@FromContext(PLAYER_DETAILS_CONTEXT) PlayerDetails player){
        //todo: Workaround implemented including a browser refresh to avoid KIDDO-3370
        browserNavigation.refresh();
        PlayerDetails displayedPlayer = customerManagerActions.getDisplayedPlayerDetailsFromCustomerManager();
        reporter.info(String.format("Displayed player: %s",displayedPlayer.toString()));
        reporter.info(String.format("Updated player: %s",player.toString()));
        assertThat(displayedPlayer).isEqualToIgnoringGivenFields(player, "email", "phone");
        assertThat(displayedPlayer.getEmail()).isEqualToIgnoringCase(player.getEmail());
        assertThat(displayedPlayer.getPhone()).matches("^\\+\\d+ " + player.getPhone() + "$");
    }

    @Then("the player details are displayed")
    public void thenThePlayerDetailsAreDisplayed(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup){
        PlayerDetailsByAreas displayedPlayer = customerManagerActions.getDisplayedPlayerDetailsByArea();
        reporter.info(displayedPlayer.toString());

        assertThat(displayedPlayer.getHeader()).containsIgnoringCase(playerSignup.getNickName());

        assertThat(displayedPlayer.getFirstArea()).isNotEmpty();
        assertThat(displayedPlayer.getSecondArea()).containsIgnoringCase(playerSignup.getEmail());
        assertThat(displayedPlayer.getStartDate()).containsIgnoringCase(String.valueOf(Year.now().getValue()));

        assertThat(customerManagerActions.isSportsDetailsDisplayed()).isEqualTo(setupActions.isSportsSupported());
    }

    @Then("the player's PPC is displayed")
    public void thenPPCsDisplayed() throws ParseException {
        String playerPPCs = customerManagerActions.getDisplayedPlayerPPCsFromCustomerManager();
        reporter.info(String.format("Player's PPCs: %s", playerPPCs));
        assertThat(playerPPCs).doesNotContain("Service is unavailable");
    }

    @When("a customer manager writes down the player account number")
    @ToContext(PLAYER_ACCOUNT_NUMBER_CONTEXT)
    public String whenACMNotesPlayerAccountNumber(){
        return customerManagerActions.getPlayerAccountNumber()
                .orElseThrow(() -> new IllegalStateException("Missing account number for player"));
    }

    @When("a customer manager searches player by account number")
    public void whenACMSearchPlayerByAccountNumber(@FromContext(PLAYER_ACCOUNT_NUMBER_CONTEXT) String accountNumber){
        customerManagerActions.searchByAccountNumber(accountNumber);
    }

}
