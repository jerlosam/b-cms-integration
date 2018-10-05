package io.crazy88.beatrix.e2e.casino.components;

import internal.katana.selenium.BrowserManager;
import internal.katana.selenium.core.browser.BrowserVendor;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Component
public class CasinoCardComponent {

    public static final String CASINO_GAME_URL = "/static/";

    public enum Game {
        ANY(-1, ""),
        A_NIGHT_WITH_CLEO(977, "a-night-with-cleo"),
        // Casino Studio Games
        ROULETTE(954, "european-roulette"),
        BLACKJACKHTML5(1052, "multi-hand-blackjack"),
        // Casino HTML5 Games
        FRUIT_FRENZY(138, "fruit-frenzy"),
        // Casino HTML5 Games Common
        BLACKJACK_ZAPPIT(123, "zappit-blackjack"),
        // CRS Static (Rival)
        ARABIAN_TALES(358, "arabian-tales"),
        // Casino Static
        CAESARS_EMPIRE(15, "caesars-empire");

        private int casinoLobbyId;
        private String slug;

        Game(int casinoLobbyId, String slug) {
            this.casinoLobbyId = casinoLobbyId;
            this.slug = slug;
        }

        public int getCasinoLobbyId() {
            return casinoLobbyId;
        }

        public String getSlug() { return slug; }
    }

    private static final By CARD = By.tagName("bx-card");

    private static final By FIRST_CARD = By.cssSelector("bx-game-categories bx-card figure");

    private static final By PLAY_PRACTICE_BUTTON = By.cssSelector("button.play-practice-button");

    private static final By PLAY_REAL_BUTTON = By.cssSelector("button.play-real-button");

    public static final By OVERLAY = By.cssSelector("div.game-detail");

    public static final By OVERLAY_IMG = By.cssSelector("bx-game-detail img");

    private static final By ROULETTE_RED_BET = By.cssSelector(".ID_46");

    private static final By FULL_DESCRIPTION_LINK = By.cssSelector(".show-long-description");

    private static final By FULL_DESCRIPTION_TEXT = By.cssSelector(".game-full-description-text");

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserWait browserWaits;

    @Autowired
    private BrowserNavigation browserNavigations;

    public void waitForCardsDisplayed() {
        browserWaits.waitForElement(FIRST_CARD);
    }

    public void openFullDescription() {
        browser.fluent().div(FULL_DESCRIPTION_LINK).click();
        browserWaits.waitForElement(FULL_DESCRIPTION_TEXT);
    }

    public void navigatesToCasinoGamePage(Game game) {
        waitForCardsDisplayed();

        Predicate<FluentWebElement> predicateCardsByCasinoLobbyId =
                fluentWebElement -> fluentWebElement.link().getAttribute("href").toString().contains(
                        String.format("/%s", game.getSlug()));
        Supplier<NotImplementedException> gameCardNoExists = () -> new NotImplementedException("Game card doesn't exist");

        if (Game.ANY.equals(game)) {
            browser.driver().findElements(FIRST_CARD).get(0).click();
            browserWaits.waitForElement(OVERLAY_IMG);
        } else if (game != null) {
            FluentWebElements cards = browser.fluent().elements(CARD);
            FluentWebElement rouletteCard =
                    cards.stream().filter(predicateCardsByCasinoLobbyId).findFirst().orElseThrow(gameCardNoExists);

            rouletteCard.figure().click();

            browserWaits.waitForElement(OVERLAY_IMG);
        } else {
            throw new NotImplementedException("Game can't be null");
        }
    }

    public void navigatesToGame(Game game) {
        navigatesToCasinoGamePage(game);
        browserNavigations.navigateWithElement(PLAY_REAL_BUTTON);
    }

    public void navigatesToAnyGame() {
        navigatesToGame(Game.ANY);
    }

    public BigDecimal playGame(Game game) {
        if (Game.ROULETTE.equals(game)) {
            return playRouletteGame();
        } else {
            throw new NotImplementedException("Game not supported");
        }

    }

    public void exitGame(Game game) {
        if (Game.ROULETTE.equals(game)) {
            exitRoulette();
        } else {
            throw new NotImplementedException("Game not supported");
        }
    }

    private BigDecimal playRouletteGame() {
        //Bet to red
        browser.fluent().div(ROULETTE_RED_BET).click();
        browser.fluent().div(By.id("spin-button")).click();
        WebDriverWait wait = new WebDriverWait(browser.driver(), 15);
        //Wait to wheel appears
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("wheel")));
        //Wait for wheel disappears
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("wheel")));
        //Check if win message is displayed
        if (browser.fluent().span(By.cssSelector("div#notification span")).isDisplayed().value()) {
            //By default 5 dollars bet so the win amount is 5.
            return new BigDecimal(5);
        } else {
            return new BigDecimal(-5);
        }
    }

    private void exitRoulette() {
        browser.fluent().div(By.id("burger-button")).click();
        browser.fluent().div(By.id("menu")).li(By.cssSelector(".btn-exit")).click();
        browser.fluent().div(By.id("popup")).div(By.cssSelector(".buttons")).spans().get(0).click();
    }

    public void playForPractice() {
        browser.fluent().button(PLAY_PRACTICE_BUTTON).click();
        browserWaits.waitForElement(GameWrapperComponent.IFRAME);
    }
}
