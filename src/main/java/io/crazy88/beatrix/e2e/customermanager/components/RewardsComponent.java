package io.crazy88.beatrix.e2e.customermanager.components;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.customermanager.dto.RewardExclusions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RewardsComponent {

    private static final By EXCLUSIONS_CONTAINER = By.cssSelector("bx-bonus-exclusions");

    private static final By EXCLUSIONS_LINK = By.linkText("Exclusions");

    private static final By EXCLUSION_ALL = By.cssSelector("div.bx-rewards-exclusions-panel-content label");

    private static final By EXCLUSION = By.cssSelector("div.bx-rewards-exclusions-input-class input");

    private static final By SAVE_EXCLUSIONS = By.cssSelector("#bx-rewards-exclusions-update-button-save");

    private static final By EXCLUSIONS_OPTIONS = By.cssSelector(".bx-rewards-exclusions-panel-content-specific-bonuses");

    private static final By AVAILABLE_LISTS_CONTAINER = By.cssSelector("bx-target-available-cm-bonus-list");

    private static final By PLAYER_LISTS_CONTAINER = By.cssSelector("bx-target-player-cm-bonus-list");

    private static final By TARGET_LISTS_LINK = By.linkText("Target Lists");

    private static final String BONUSES_LIST = "//bx-panel[contains(@class, 'bx-target-available-bonus-panel')]//table[contains(@class, 'table-list')]//tbody[contains(@class, 'list-body')]";

    private static final String BONUS_AVAILABLE_BY_CODE = "//bx-panel[contains(@class, 'bx-target-available-bonus-panel')]//table[contains(@class, 'table-list')]//tbody[contains(@class, 'list-body')]//td//span[text()=' %s ']";

    private static final String PLAYER_BONUS_LIST = "//bx-panel[contains(@class, 'bx-target-player-bonus-panel')]//table[contains(@class, 'table-list')]//tbody[contains(@class, 'list-body')]";

    private static final String PLAYER_BONUS_LIST_BY_CODE = "//bx-panel[contains(@class, 'bx-target-player-bonus-panel')]//table[contains(@class, 'table-list')]//tbody[contains(@class, 'list-body')]//td//span[text()=' %s ']";

    private static final By ADD_BUTTON = By.className("add-link");

    private static final By REMOVE_BUTTON = By.className("remove-link");

    private static final By LINK = By.tagName("a");

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserForm browserForm;

    @Autowired
    private BrowserNavigation browserNavigations;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private Reporter reporter;


    public void navigateToRewardExclusions(){
        browserNavigations.navigateWithElement(EXCLUSIONS_LINK);
    }

    public RewardExclusions getRewardExlusions() {
        browserWait.waitForElement(EXCLUSIONS_CONTAINER);
        browserWait.waitForElement(EXCLUSIONS_OPTIONS);

        List<WebElement> exclusionsElements = browserForm.getAllElements(EXCLUSION);
        Map<String, Boolean> exclusions = new HashMap<>();
        exclusionsElements.forEach(ex -> exclusions.put(ex.getAttribute("id"), ex.isSelected()));
        return RewardExclusions.builder()
                .rewardExclusions(exclusions)
                .build();

    }

    public void excludeAllRewards() {
        browserWait.waitForElement(EXCLUSIONS_CONTAINER);
        browserForm.clickOnElement(EXCLUSIONS_CONTAINER, EXCLUSION_ALL);
        browserForm.submitForm(SAVE_EXCLUSIONS);
    }

    public void navigateToTargetLists(){
        browserNavigations.navigateWithElement(TARGET_LISTS_LINK);
    }

    public String clickOnAddButtonOfFirstBonus() {
        browserWait.waitForElement(AVAILABLE_LISTS_CONTAINER);
        FluentWebElement firstAddRow = getFirstAvailableBonusRow();
        String bonusCode =firstAddRow.elements(By.tagName("td")).stream().findFirst().get().getText().toString();
        firstAddRow.element(ADD_BUTTON).element(LINK).click();
        return bonusCode;
    }

    public boolean bonusDisplayedPlayerLists(String expectedBonusCode) {
        browserWait.waitForElement(By.xpath(String.format(PLAYER_BONUS_LIST_BY_CODE, expectedBonusCode)));
        String bonusCode = getFirstTargetedBonusRow().elements(By.tagName("td")).stream().findFirst().get().getText().toString();
        return bonusCode.equalsIgnoreCase(expectedBonusCode);
    }

    public String clickOnRemoveButtonOfFirstBonus() {
        browserWait.waitForElement(PLAYER_LISTS_CONTAINER);
        FluentWebElement firstRemoveRow = getFirstTargetedBonusRow();
        String bonusCode = firstRemoveRow.elements(By.tagName("td")).stream().findFirst().get().getText().toString();
        firstRemoveRow.element(REMOVE_BUTTON).element(LINK).click();
        return bonusCode;
    }

    private FluentWebElement getFirstTargetedBonusRow() {
        FluentWebElements rows = browser.fluent()
                .element(By.xpath(PLAYER_BONUS_LIST))
                .elements(By.className("bx-player-bonus-item"));
        return rows.stream().findFirst().orElseThrow(IllegalStateException::new);
    }

    private FluentWebElement getFirstAvailableBonusRow() {
        FluentWebElements rows = browser.fluent()
                .element(By.xpath(String.format(BONUSES_LIST)))
                .elements(By.className("bx-available-bonus-item"));
        return rows.stream().findFirst().orElseThrow(IllegalStateException::new);
    }

    public boolean bonusDisplayedAvailableLists(String expectedBonusCode) {
        browserWait.waitForElement(By.xpath(String.format(BONUS_AVAILABLE_BY_CODE, expectedBonusCode)));
        String bonusCode = getFirstAvailableBonusRow().elements(By.tagName("td")).stream().findFirst().get().getText().toString();
        return bonusCode.equalsIgnoreCase(expectedBonusCode);
    }
}
