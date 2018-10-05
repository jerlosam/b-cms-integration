package io.crazy88.beatrix.e2e.casino.actions;

import internal.katana.selenium.BrowserManager;
import internal.qaauto.picasso.java.client.core.dto.PicassoRectangle;
import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.casino.components.CasinoCardComponent;
import io.crazy88.beatrix.e2e.casino.components.GameWrapperComponent;
import io.crazy88.beatrix.e2e.visual.ImageValidation;
import io.crazy88.beatrix.e2e.visual.VisualUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.seleniumhq.selenium.fluent.TestableValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Casino Games Actions
 * Created by jolalla on 7/3/17.
 */
@Component
public class CasinoGameActions {

    private static final By BLACKJACK_PLAY_BUTTON = By.id("action-button-play");

    private static final By FRUIT_FRENZY_MENU_BUTTON = By.id("menuContainer");

    private static final By BLACKJACK_ZAPPIT_DEAL_BUTTON =
            By.xpath("//div[contains(@class,'lime-director')]/descendant::div[text()='DEAL'][2]");

    private static final By ARABIAN_TALES_FLASH_OBJECT = By.id("myGame");

    private static final By CAESARS_EMPIRE_FLASH_GAME_IFRAME = By.id("flashGameIframe");

    @Autowired
    private GameWrapperComponent gameWrapperComponent;

    @Autowired
    private BrowserValidation browserValidations;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private VisualUtils visualUtils;

    public void waitForGameIsLoaded(CasinoCardComponent.Game game) {
        switch (game) {
        case BLACKJACKHTML5:
            browserWait.waitUntil(60, ExpectedConditions.visibilityOfElementLocated(BLACKJACK_PLAY_BUTTON));
            break;
        case FRUIT_FRENZY:
            browserWait.waitUntil(60, ExpectedConditions.visibilityOfElementLocated(FRUIT_FRENZY_MENU_BUTTON));
            break;
        case BLACKJACK_ZAPPIT:
            browserWait.waitUntil(60, ExpectedConditions.visibilityOfElementLocated(BLACKJACK_ZAPPIT_DEAL_BUTTON));
            break;
        case ARABIAN_TALES:
            browserWait.waitUntil(60, ExpectedConditions.visibilityOfElementLocated(ARABIAN_TALES_FLASH_OBJECT));
            break;
        case CAESARS_EMPIRE:
            browserWait.waitUntil(60, ExpectedConditions.visibilityOfElementLocated(CAESARS_EMPIRE_FLASH_GAME_IFRAME));
            break;
        default:
            throw new NotImplementedException("Game waiter not implemented");
        }

    }

    public void switchToGameWrapper() {
        gameWrapperComponent.switchToGameFrame();
    }

    public List<ImageValidation> getVisualGameValidations(CasinoCardComponent.Game game) {
        List<ImageValidation> images = new ArrayList<>();
        //Wait 30 seconds for no animation with a chec
        browserWait.waitForNoAnimations(30, 1500);

        if (CasinoCardComponent.Game.ARABIAN_TALES.equals(game) || CasinoCardComponent.Game.CAESARS_EMPIRE.equals(game)) {
            TestableValue<Dimension> size = browser.fluent().element(By.tagName("body")).getSize();
            double height = (double) size.value().getHeight() / 2d;
            PicassoRectangle ignore = new PicassoRectangle(0, 0, size.value().getWidth(), (int) height);
            images.add(
                    ImageValidation.builder().snapshot(
                            visualUtils.captureImage(game.name(), GameWrapperComponent.IFRAME)).ignoredRegionList(
                            Arrays.asList(ignore)).build());
        } else if (CasinoCardComponent.Game.FRUIT_FRENZY.equals(game)) {
            TestableValue<Dimension> size = browser.fluent().element(By.tagName("body")).getSize();
            double height = (double) size.value().getHeight() / 2d;
            PicassoRectangle ignore = new PicassoRectangle(0, 0, size.value().getWidth(), (int) height);
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(game.name())).ignoredRegionList(
                    Arrays.asList(ignore)).build());
        } else {
            images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(game.name())).build());
        }
        return images;
    }
}
