package io.crazy88.beatrix.e2e.casino;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.casino.actions.CasinoActions;
import io.crazy88.beatrix.e2e.casino.actions.CasinoGameActions;
import io.crazy88.beatrix.e2e.casino.components.CasinoCardComponent;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import io.crazy88.beatrix.e2e.visual.ImageValidation;
import io.crazy88.beatrix.e2e.visual.VisualUtils;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.ScreenOrientation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

@Component
public class CasinoSteps {

    private static final String CASINO_WINNINGS_CONTEXT = "casinoWinnings";

    private static final String CASINO_GAME_CONTEXT = "casinoGame";

    public static final String IMAGES_TO_COMPARE = "IMAGES_TO_COMPARE";

    @Autowired
    private CasinoActions casinoActions;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserNavigation browserNavigation;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private CasinoGameActions casinoGameActions;

    @Autowired
    private VisualUtils visualUtils;

    @Autowired
    private Reporter reporter;

    @Given("rotate to $orientation")
    public void rotate(final ScreenOrientation orientation) {
        browserNavigation.rotateDevice(orientation);
    }

    @When("a player navigates to $game game page")
    public void navigateToCasinoGamePage(CasinoCardComponent.Game game) {
        playerNavigationActions.enterSite();
        casinoActions.navigateToCasinoGamePage(game);
    }

    @When("the player opens the description of the game")
    public void openFullDescription() {
        casinoActions.openFullDescription();
    }

    @When("the player plays $game game")
    @ToContext(CASINO_WINNINGS_CONTEXT)
    public BigDecimal playGame(CasinoCardComponent.Game game,
            @FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        playerNavigationActions.enterSite();
        playerNavigationActions.logIn(playerSignup.getEmail(), playerSignup.getPassword());
        return casinoActions.navigateAndPlayGame(game);
    }

    @When("the player navigates to a casino game")
    public void whenThePlayerOpensACasinoGame(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        playerNavigationActions.enterSite();
        playerNavigationActions.logIn(playerSignup.getEmail(), playerSignup.getPassword());
        casinoActions.navigateToAnyCasinoGame();
    }

    @When("the player navigates to $game game")
    @ToContext(CASINO_GAME_CONTEXT)
    public CasinoCardComponent.Game whenThePlayerOpensACasinoGame(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup,
            CasinoCardComponent.Game game) {
        playerNavigationActions.enterSite();
        playerNavigationActions.logIn(playerSignup.getEmail(), playerSignup.getPassword());
        casinoActions.navigateToGame(game);
        return game;
    }

    @When("visual game is displaying")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> visualGameDisplaying(@FromContext(CASINO_GAME_CONTEXT) CasinoCardComponent.Game game) {
        return casinoGameActions.getVisualGameValidations(game);
    }

    @Then("a casino game is launched")
    public void thenACasinoGameIsLaunched() {
        assertThat(casinoActions.isPlayerOnACasinoGame()).isTrue();
    }

    @Then("the casino game is loaded")
    public void waitForGameIsLoaded(@FromContext(CASINO_GAME_CONTEXT) CasinoCardComponent.Game game) {
        try {
            casinoGameActions.switchToGameWrapper();
        } catch (Exception e) {
            reporter.warn("Game without game wrapper");
        }
        casinoGameActions.waitForGameIsLoaded(game);
    }

}
