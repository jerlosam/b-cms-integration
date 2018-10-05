package io.crazy88.beatrix.e2e.rewardmanager.bonus.components;

import static java.lang.String.format;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.bonus.dto.Bonus;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class BonusDashboardComponent {
    
    private static final By NEW_BONUS_FIRST_TIME_DEPOSIT_BUTTON = By.className("bx-first-time-deposit");

    private static final By BRAND_SELECTOR = By.tagName("bx-brand-select");

    private static final String FIND_BONUS_BY_INTERNAL_NAME_AND_CODE = "//td[contains(@class, 'bx-bonus-name')]//span[contains(text(),'%s')]";

    private static final String FIND_DRAFT_BY_INTERNAL_CODE = "//article[contains(@class,'draft-item')]//section[contains(@class,'draft-content')]//a[contains(text(),'%s')]";

    private static final By BONUSES_LIST = By.id("bonusManagementList");

    private static final By DRAFTS_LIST = By.tagName("bx-draft-list");

    private static final By DELETE_BUTTON = By.className("bx-bonus-disable");

    private static final By EDIT_BUTTON = By.className("bx-bonus-edit");

    private static final By CONFIRM_BUTTON = By.className("custom-cta primary");

    private static final By DIALOG = By.tagName("bx-dialog");

    private static final By LINK = By.tagName("a");
    
    private static final String INTERNAL_NAME_AND_CODE = "%s / %s";

    @Autowired
    private BrowserForm browserForms;

    @Autowired
    private BrowserNavigation browserNavigations;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserWait browserWaits;
    
    public void navigateToCreateFirstTimeDeposit() {
        browserNavigations.navigateWithElement(NEW_BONUS_FIRST_TIME_DEPOSIT_BUTTON);
    }

    public void selectBrand(String brand) {
        browserForms.selectValueOnSelector(BRAND_SELECTOR, brand);
    }

    public boolean isBonusDisplayed(Bonus bonus){
        browserWaits.waitForElement(BONUSES_LIST);
        browserWaits.waitForAngular();
        return !browser.driver().findElements(getBonusSelector(bonus)).isEmpty();
    }

    public boolean isDraftDisplayed(Bonus bonusDraft){
        browserWaits.waitForElement(DRAFTS_LIST);
        browserWaits.waitForAngular();

        return !browser.driver().findElements(getDraftSelector(bonusDraft)).isEmpty();
    }

    public void deleteBonus(Bonus bonus){
        browserWaits.waitForElement(BONUSES_LIST);
        browserWaits.waitForAngular();

        clickOnDeleteBonus(bonus);

        clickOnConfirmButton();

        browserWaits.waitUntil(5, __ -> !isBonusDisplayed(bonus));
    }

    public void editDraft(Bonus bonusDraft){
        browserWaits.waitForElement(DRAFTS_LIST);
        browserWaits.waitForAngular();

        clickOnDraftTitleToEdit(bonusDraft);
    }

    private void clickOnDeleteBonus(Bonus bonus) {
        Optional<FluentWebElement> bonusRow = getBonusRow(bonus);
        bonusRow.ifPresent(row -> row.element(DELETE_BUTTON).element(LINK).click());
    }

    private void clickOnEditBonus(Bonus bonus) {
        Optional<FluentWebElement> bonusRow = getBonusRow(bonus);
        bonusRow.ifPresent(row -> row.element(EDIT_BUTTON).element(LINK).click());
    }

    private Optional<FluentWebElement> getBonusRow(Bonus bonus) {
        FluentWebElements rows = browser.fluent().element(BONUSES_LIST).elements(By.tagName("tr"));
        return rows.stream().filter(fluentWebElement ->
                Optional.ofNullable(fluentWebElement.element(getBonusSelector(bonus))).isPresent()
        ).findFirst();
    }

    public void clickOnDraftTitleToEdit(Bonus bonusDraft){
        browser.driver().findElements(getDraftSelector(bonusDraft)).get(0).click();
    }

    public void clickOnBonusTitleToEdit(Bonus bonus){
        browser.driver().findElements(getBonusSelector(bonus)).get(0).click();
    }

    private void clickOnConfirmButton() {
        browserWaits.waitForElement(DIALOG);
        browser.fluent().element(CONFIRM_BUTTON).click();
    }

    private By getBonusSelector(Bonus bonus) {
        return By.xpath(String.format(FIND_BONUS_BY_INTERNAL_NAME_AND_CODE, 
        		format(INTERNAL_NAME_AND_CODE, bonus.getInternalName(), bonus.getInternalCode())));
    }

    private By getDraftSelector(Bonus draft) {
        String draftTitle = StringUtils.isNotBlank(draft.getInternalName())? draft.getInternalName() : draft.getInternalCode();
        return By.xpath(String.format(FIND_DRAFT_BY_INTERNAL_CODE, draftTitle));
    }

    public void editBonus(Bonus bonus){
        browserWaits.waitForElement(BONUSES_LIST);
        browserWaits.waitForAngular();

        clickOnEditBonus(bonus);
    }
}
