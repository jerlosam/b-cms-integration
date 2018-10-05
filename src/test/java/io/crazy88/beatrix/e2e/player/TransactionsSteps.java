package io.crazy88.beatrix.e2e.player;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.core.reporter.Reporter;
import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;
import io.crazy88.beatrix.e2e.player.actions.PlayerAccountMenuActions;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import io.crazy88.beatrix.e2e.wallet.actions.WalletActions;

@Component
public class TransactionsSteps {

    public static final String WALLET_TRANSACTION_REF = "walletTransactionRef";

    @Autowired
    private PlayerAccountMenuActions playerAccountMenuActions;

    @Autowired
    private CustomerManagerActions customerManagerActions;

    @Autowired
    private WalletActions walletActions;

    @Autowired
    private Reporter reporter;

    @Then("two bonus transactions are displayed to the player")
    public void thenOneBonusTransactionIsDisplayed(){
        AssertionsHelper.retryUntilSuccessful(15, 3000, () -> {
            int numberOfStatements = playerAccountMenuActions.getNumberOfBonusStatements();
            assertThat(numberOfStatements).isEqualTo(2);
        });
    }

    @When("the player places a single bet")
    @ToContext(WALLET_TRANSACTION_REF)
    public String whenPlayerPlacesASingleBet(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        customerManagerActions.searchByEmailOnCustomerManager(playerSignup.getEmail());
        customerManagerActions.performAManualAccountEntry();
        return walletActions.createAStatement(playerSignup);
    }

    @Then("the statement is displayed in customer manager")
    public void thenTheWithdrawableBalanceIsZero(@FromContext(WALLET_TRANSACTION_REF) String transactionRef) {
        waitUntilIsProcessed(1000);

        AssertionsHelper.retryUntilSuccessful(() -> {
            boolean isDisplayed = customerManagerActions.isTransactionWithReferenceDisplayed(transactionRef);
            assertThat(isDisplayed).isTrue();
        });
    }

    private void waitUntilIsProcessed(long timeToWait) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeToWait);
        } catch (InterruptedException e) {
            reporter.error(e.getMessage());
        }

    }
}
