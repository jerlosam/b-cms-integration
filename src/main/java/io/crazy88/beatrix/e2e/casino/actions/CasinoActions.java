package io.crazy88.beatrix.e2e.casino.actions;

import io.crazy88.beatrix.e2e.casino.components.CasinoCardComponent;
import io.crazy88.beatrix.e2e.casino.components.GameWrapperComponent;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CasinoActions {

    @Autowired
    private CasinoCardComponent casinoCard;

    @Autowired
    private GameWrapperComponent gameWrapperComponent;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;


    public void navigateToCasinoGamePage(CasinoCardComponent.Game game) {
        casinoCard.navigatesToCasinoGamePage(game);
    }

    public BigDecimal navigateAndPlayGame(CasinoCardComponent.Game game) {
        casinoCard.navigatesToGame(game);
        BigDecimal balanceUpdate = casinoCard.playGame(game);
        casinoCard.exitGame(game);
        return balanceUpdate;
    }

    public void navigateToAnyCasinoGame(){
        casinoCard.navigatesToAnyGame();
    }

    public void navigateToGame(CasinoCardComponent.Game game) {
        casinoCard.navigatesToGame(game);
    }

    public boolean isPlayerOnACasinoGame(){
        return gameWrapperComponent.isDisplayed();
    }
    public void openFullDescription() {
        casinoCard.openFullDescription();
    }

    public void playForPractice() {
        casinoCard.playForPractice();
    }

    public void switchToRealMode() {
        gameWrapperComponent.switchToRealMode();
    }
}
