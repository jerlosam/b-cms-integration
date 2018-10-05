package io.crazy88.beatrix.e2e.player;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.player.actions.PlayerAccountMenuActions;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class WalletSteps {

    public final static String KNOWN_BALANCE_CONTEXT = "knownBalance";

    @Autowired
    private BrowserManager browserManager;

    @Autowired
    private PlayerAccountMenuActions playerAccountMenuActions;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Given("the player's balance is zero")
    @ToContext(KNOWN_BALANCE_CONTEXT)
    public BigDecimal givenThePlayerBalanceIsZero(){
        return BigDecimal.ZERO;
    }

    @Then("the player has positive balance")
    public void thenThePlayerHasPositiveBalance() {
        if(browserManager.browserDriver().browser().isMobile()) {
            playerNavigationActions.navigateToPreviousPage();
        }
        AssertionsHelper.retryUntilSuccessful(30, 2000, () -> {
            BigDecimal currentBalance = playerAccountMenuActions.getPlayersPlayableBalance();
            assertThat(currentBalance).isGreaterThan(BigDecimal.ZERO);
        });
    }

    @Then("the player's withdrawable balance is Zero")
    public void thenTheWithdrawableBalanceIsZero() {
        AssertionsHelper.retryUntilSuccessful(() -> {
            BigDecimal currentBalance = playerAccountMenuActions.getPlayersWithdrawableBalance();
            assertThat(currentBalance).isEqualByComparingTo(BigDecimal.ZERO);
        });
    }

    @Then("the player has Zero balance")
    public void thenThePlayerZeroBalance() {
        if(browserManager.browserDriver().browser().isMobile()) {
            playerNavigationActions.navigateToPreviousPage();
        }

        AssertionsHelper.retryUntilSuccessful(30, 2000, () -> {
            assertThat(playerAccountMenuActions.bonusFundsBalanceDoesNotDisplay()).isTrue();
        });

    }
}
