package io.crazy88.beatrix.e2e.rewardmanager.bonus.components;

import io.crazy88.beatrix.e2e.E2EProperties;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.bonus.dto.Bonus;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class BonusEditionComponent {

	@Autowired
	private BrowserManager browser;

	@Autowired
	private BrowserForm browserForms;

	@Autowired
	private BrowserWait browserWaits;

	@Autowired
	private BrowserNavigation browserNavigations;

	@Autowired
	private E2EProperties properties;

	private static final By REGIONS = By.className("single-region");

	// Wizard
	private static final By SAVE_AND_CONTINUE_BUTTON = By.id("bx-save-and-continue");
	private static final By SAVE_AS_DRAFT_BUTTON = By.id("bx-save-draft");
	private static final By SAVE_BUTTON = By.id("bx-save");
	private static final By DISCARD_BUTTON = By.id("bx-discard");
	private static final By CONFIRM_BUTTON = By.cssSelector(".modal-overlay .custom-cta.primary");
	private static final By DIALOG = By.tagName("bx-dialog");
	public static final By WIZARD = By.cssSelector(".wizard");

	// Dates step
	private static final By STARTS_AT = By.id("bonusDates-startsAt");
	private static final By ENDS_AT = By.id("bonusDates-endsAt");
	private static final By DAYS_REDEMPTION = By.id("bonusDates-expiresInDays");
	private static final By EXPIRED_AT = By.id("bonusDates-expiresOnDate");
	private static final By EXPIRE_RADIO_BUTTON_EXPIRATION_DAYS = By.xpath("//div[./input[@id='expiration-type-radio-1']]");
	private static final By EXPIRE_RADIO_BUTTON_EXPIRATION_DATE = By.xpath("//div[./input[@id='expiration-type-radio-2']]");
	private static final By RADIOBUTTON_CONTAINER = By.tagName("bx-form");
	public static final By INTERNAL_CODE = By.cssSelector(".bx-panel-header span:nth-child(2)");

	// Deposit details step
	private static final By LOCK_AMOUNT = By.id("depositDetails-lockAmount");

	// Reward step
	private static final By AMOUNT = By.cssSelector("label[for='reward-reward']");
	private static final By AMOUNT_PERCETAGE = By.cssSelector("label[for='amountTypes_PERCENTAGE']");

	// Rules step
	private static final By RULES_BASE_SELECTOR = By.id("bonusRules-base");
	private static final By RULES_BONUS_PRIORITY_SELECTOR = By.id("bonusRules-bonusPriority");
	private static final By RULES_TEMPLATES = By.id("bonusRules-contributionTemplate");
	private static final By FIRST_BASE_OPTION = By.cssSelector("#bonusRules-base ~ ul>li:first-of-type");
	private static final By FIRST_BONUS_PRIORITY_OPTION = By.cssSelector("#bonusRules-bonusPriority ~ ul>li:first-of-type");
	private static final By RULES_BONUS_FACTOR = By.id("bonusRules-factors");
	private static final By RULES_TEMPLATE_SELECTOR = By.id("bonusRules-contributionTemplate");
	private static final By FIRST_TEMPLATE_OPTION = By
			.cssSelector("#bonusRules-contributionTemplate ~ ul>li:first-of-type");
	private static final String TEMPLATE_OPTION_BY_NAME = "//input[@id='bonusRules-contributionTemplate']/../ul/li[contains(text(), '%s')]";
	// Names step
	private static final By BONUS_CODES = By.cssSelector(".ng2-tag-input__text-input.add-tag-field");
	private static final By INTERNAL_NAME = By.id("bonusNames-internalName");
	private static final By EXTERNAL_NAME = By.id("bonusNames-externalName");
	private static final By DESCRIPTION = By.id("bonusNames-description");
	private static final By TAB_CHILDREN_SELECTOR = By.cssSelector("bx-tabs>div>nav>ul>li");
	// Distribution Step
	private static final By DISTRIBUTION_AVAILABILITY = By.id("bonusDistribution-availability");

	public String fillWizardWithCurrency(Bonus bonus, String currency) {

		String internalCode = getInternalCode();


		browserWaits.waitForElement(REGIONS);
		fillRegionsStep(bonus);

		saveAndContinueStep();

		// Dates step
		browserWaits.waitForElement(STARTS_AT);
		fillDatesStep(bonus);
		saveAndContinueStep();

		// Deposit details step
		browserWaits.waitForElement(LOCK_AMOUNT);
		fillDepositDetailsStep(bonus);
		saveAndContinueStep();

		// Reward step
		browserWaits.waitForElement(AMOUNT);
		fillRewardStepWithCurrency(bonus, currency);
		saveAndContinueStep();

		// Rules step
		browserWaits.waitForElement(RULES_BASE_SELECTOR);
		browserWaits.waitForElement(RULES_TEMPLATES);
		fillRulesStep(bonus);
		saveAndContinueStep();

		// Names step
		browserWaits.waitForElement(INTERNAL_NAME);
		fillNamesStep(bonus);
		saveAndContinueStep();

		// Distribution steps
		browserWaits.waitForElement(DISTRIBUTION_AVAILABILITY);
		browserWaits.waitForElement(SAVE_BUTTON);

		return internalCode;

	}

	public String saveAndContinueAllSteps(Bonus bonus) {

		String internalCode = getInternalCode();

		browserWaits.waitForElement(REGIONS);
		saveAndContinueStep();

		// Dates step
		browserWaits.waitForElement(STARTS_AT);
		saveAndContinueStep();

		// Deposit details step
		browserWaits.waitForElement(LOCK_AMOUNT);
		saveAndContinueStep();

		// Reward step
		browserWaits.waitForElement(AMOUNT);
		saveAndContinueStep();

		// Rules step
		browserWaits.waitForElement(RULES_BASE_SELECTOR);
		browserWaits.waitForElement(RULES_TEMPLATES);
		saveAndContinueStep();

		// Names step
		browserWaits.waitForElement(INTERNAL_NAME);
		saveAndContinueStep();

		// Distribution steps
		browserWaits.waitForElement(DISTRIBUTION_AVAILABILITY);
		browserWaits.waitForElement(SAVE_BUTTON);

		return internalCode;

	}



	private String getInternalCode() {
        browserWaits.waitForElement(INTERNAL_CODE);
        browserWaits.waitUntil(__ ->
            browser.driver().findElement(INTERNAL_CODE).getText().contains("- ")
        );
        String internalCode = browser.driver().findElement(INTERNAL_CODE).getText();
        return internalCode.replace("- ","");
	}

	public void fillRegionsStep(Bonus bonus) {
		browserWaits.waitForElement(REGIONS);
	}

	public void fillDatesStep(Bonus bonus) {
		browserForms.fillInDateField(STARTS_AT, bonus.getStartDate());
		browserForms.fillInDateField(ENDS_AT, bonus.getEndDate());
		
		if (bonus.getDaysAfterRedemption() != null) {
			if (!bonus.getDaysAfterRedemption().isEmpty()) {
				browserForms.fillTextField(DAYS_REDEMPTION, bonus.getDaysAfterRedemption());
			} else {
				browserForms.clearFieldByKeys(DAYS_REDEMPTION);
			}
		}
		if (bonus.getExpiredDate() != null) {
			browserForms.clickOnElement(RADIOBUTTON_CONTAINER, EXPIRE_RADIO_BUTTON_EXPIRATION_DATE);
			if (!bonus.getExpiredDate().isEmpty()) {
				browserForms.fillInDateField(EXPIRED_AT, bonus.getExpiredDate());
			} else {
				browserForms.clickOnElement(RADIOBUTTON_CONTAINER, EXPIRE_RADIO_BUTTON_EXPIRATION_DAYS);
			}
		}
	}

	public void fillDepositDetailsStep(Bonus bonus) {
		browserForms.fillTextField(LOCK_AMOUNT, bonus.getLockAmount().toString());
	}

	public void fillRewardStepWithCurrency(Bonus bonus, String currency) {
		browserForms.clickOnElement(By.className("custom-checkbox"), AMOUNT);
		browserForms.clickOnElement(By.className("custom-radio"), AMOUNT_PERCETAGE);
		browserForms.fillTextField(By.id(currency),
				bonus.getAmounts().entrySet().iterator().next().getValue().toString());
		browserForms.submitForm(By.id("bonusRewardSaveButton"));
	}

	public void fillRulesStep(Bonus bonus) {
	    browserWaits.waitForClickableElement(RULES_BASE_SELECTOR);
		browserForms.setValueOnSelect(RULES_BASE_SELECTOR, FIRST_BASE_OPTION);
		browserForms.setValueOnSelect(RULES_BONUS_PRIORITY_SELECTOR, FIRST_BONUS_PRIORITY_OPTION);
		browserForms.fillTextField(RULES_BONUS_FACTOR, bonus.getFactor().toString());
		if (StringUtils.isNotBlank(bonus.getTemplateName())) {
			browserForms.setValueOnSelect(RULES_TEMPLATE_SELECTOR,
					By.xpath(String.format(TEMPLATE_OPTION_BY_NAME, bonus.getTemplateName())));
		} else {
			browserForms.setValueOnSelect(RULES_TEMPLATE_SELECTOR, FIRST_TEMPLATE_OPTION);
		}
	}

	public void fillNamesStep(Bonus bonus) {
		browserForms.fillTextField(INTERNAL_NAME, bonus.getInternalName());

		if (bonus.getCodes()!=null && !bonus.getCodes().isEmpty()) {
            browserForms.fillTextFieldAndSpace(BONUS_CODES, bonus.getCodes().get(1));
            browserForms.fillTextFieldAndEnter(BONUS_CODES, bonus.getCodes().get(0));
        }

		browser.driver().findElements(TAB_CHILDREN_SELECTOR).stream().forEach(webElement -> {
			webElement.click();
			browserForms.fillTextField(EXTERNAL_NAME, bonus.getExternalName());
			browserForms.fillTextAreaField(DESCRIPTION, bonus.getDescription());
		});

	}

	public void saveAndContinueStep() {
		browserWaits.waitForElement(SAVE_AND_CONTINUE_BUTTON);
		browser.fluent().element(SAVE_AND_CONTINUE_BUTTON).click();
	}

	public void saveAsDraft() {
		browser.fluent().element(SAVE_AS_DRAFT_BUTTON).click();
		browserWaits.waitForElement(DIALOG);
		browser.driver().findElement(CONFIRM_BUTTON).click();
	}

	public void submitForm() {
		browserNavigations.navigateWithElement(SAVE_BUTTON);
	}

	public void discardForm() {
		browser.fluent().button(DISCARD_BUTTON).click();
	}
}
