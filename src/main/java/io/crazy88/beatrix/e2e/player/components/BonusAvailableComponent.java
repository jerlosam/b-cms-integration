package io.crazy88.beatrix.e2e.player.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BonusAvailableComponent {

    private static final By BONUSES = By.tagName("tr");

    private static final By UPFRONT_BONUS_CLAIM_BUTTON = By.xpath("//button[contains(.,'Claim Bonus')]");

    private static final By BONUS_FORFEIT_BUTTON = By.cssSelector("bx-account-bonus-active-list button");

    private static final By FORFEIT_CONFIRMATION_BUTTON = By.xpath("//bx-notification2//button[contains(.,'Forfeit')]");

    private static final By BONUSES_LIST = By.className("bonuses-list");

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserWait browserWaits;

    public void claimFirstUpfrontBonus(){
        browserWaits.waitForElement(BONUSES_LIST);
        browserWaits.waitForAngular();

        try {
            browserWaits.waitForClickableElement(UPFRONT_BONUS_CLAIM_BUTTON);
        }catch (Exception e){
            throw new IllegalStateException("No Upfront Bonuses Found. Maybe is needed to create one manually", e);
        }

        browser.driver().findElement(UPFRONT_BONUS_CLAIM_BUTTON).click();
        browserWaits.waitForAngular();

    }

    public void forfeitBonus(){
        browserWaits.waitForElement(BONUSES_LIST);
        browserWaits.waitForAngular();

        try {
            browserWaits.waitForClickableElement(BONUS_FORFEIT_BUTTON);
        }catch (Exception e){
            throw new IllegalStateException("No Active Bonuses Found.", e);
        }

        browser.driver().findElement(BONUS_FORFEIT_BUTTON).click();
        browserWaits.waitForAngular();

        try {
            browserWaits.waitForClickableElement(FORFEIT_CONFIRMATION_BUTTON);
        }catch (Exception e){
            throw new IllegalStateException("Notification not displayed to forfeit the bonus", e);
        }

        browser.driver().findElement(FORFEIT_CONFIRMATION_BUTTON).click();
        browserWaits.waitForAngular();

    }

    public boolean isPlayerInBonusScreen(){
        return (!browser.driver().findElements(BONUSES_LIST).isEmpty());
    }
}
