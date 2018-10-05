package io.crazy88.beatrix.e2e.rewardmanager.loyalty.components;

import static io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.TokenType.CATEGORY;
import static io.crazy88.beatrix.e2e.rewardmanager.loyalty.factory.ProgramFactory.EDITION_ADD_TEXT;
import static io.crazy88.beatrix.e2e.rewardmanager.loyalty.utils.FileUtils.getFileName;
import static java.lang.String.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Contribution;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Program;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Tier;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.TokenType;
import io.crazy88.beatrix.e2e.visual.ImageValidation;
import io.crazy88.beatrix.e2e.visual.VisualUtils;

@Component
public class WizardComponent {

    // Names
    public static final By NAMES_INPUT = By.cssSelector("input[id$='-programName']");

    public static final By REWARD_NAMES_INPUT = By.cssSelector("input[id$='-rewardName']");

    public static final String CONTRIBUTION_POINTS_PATTERN = "%s-contribution-%s-points-%s-%s";

    public static final By CONTRIBUTION_POINTS_GENERIC_INPUT = By.cssSelector("[id^='casino-contribution-handle-points-']");

    public static final String CONTRIBUTION_AMOUNT_PATTERN = "%s-contribution-%s-amount-%s-%s";

    public static final By CONTRIBUTION_AMOUNT_GENERIC_INPUT = By.cssSelector("[id^='casino-contribution-handle-amount-']");

    public static final By TIER_NAMES_INPUTS = By.cssSelector("[id^='tierName-']");

    public static final By REWARD_POINTS_LIFESPAN = By.id("expirationDate-months");

    public static final By LINK_INPUT = By.id("link");

    public static final By ADD_EXCEPTION_POINTS_EARNED = By.cssSelector("[id^='points-']");

    public static final By ADD_EXCEPTION_MONEY_SPENT = By.cssSelector("[id^='amount-']");

    public static final By TIER_VALUES_ERROR_MSG = By.className("custom-notification");

    private static final Map<String, Integer> CASINO_CATEGORIES = new HashMap<>();

    // Wizard
    private static final By MAIN_CONTAINER = By.className("content");

    private static final By SAVE_AND_CONTINUE_BUTTON = By.id("bx-save-and-continue");

    private static final By DISCARD_BUTTON = By.id("bx-discard");

    private static final By STEP_TITLE = By.cssSelector("div.step-title");

    // Regions
    private static final By REGIONS_STEP = By.className("bx-ljl-regions");

    private static final By TAB_CHILDREN_SELECTOR = By.cssSelector("bx-tabs>div>nav>ul>li");

    // Earning points
    private static final By EARNING_POINTS = By.className("bx-ljl-earning-points-program");

    private static final By SELECTED_CURRENCY = By.cssSelector("input#currencies");

    private static final By CASINO_EARNING_POINTS = By.id("casino");

    private static final By CATEGORY_HEADER = By.cssSelector("[id^='contribution-']");

    private static final String CONTRIBUTION_BUTTON_PATTERN = "contribution-exception-button-%s";

    private static final String GAME_POINTS_PATTERN = "points-%s";

    private static final String GAME_AMOUNT_PATTERN = "amount-%s";

    private static final By MODAL_CONTENT = By.className("modal-content");

    private static final By BUTTON_SAVE_EXCEPTION = By.id("bx-exceptions-save");

    private static final By CONTRIBUTION_BUTTON = By.cssSelector("[id^='contribution-exception-button-']");

    // Currency Setup
    private static final By CURRENCY_SETUP = By.cssSelector("div.bx-ljl-currency-setup");

    private static final By CURRENCY_SETUP_TABS = By.cssSelector("div.bx-ljl-currency-setup div.bx-tabs li");

    private static final String EXCEPTION_AMOUNT_PATTERN = "exception-%s-amount-%s";

    // Tier names
    private static final By TIER_NAMES = By.cssSelector("div.bx-ljl-tier-names");

    private static final By TIER_NAMES_TABS = By.cssSelector("div.bx-ljl-tier-names div.bx-tabs li");

    private static final By TIER_NAMES_ADD_BUTTON = By.id("addTier");

    // Tier Values
    private static final By TIER_VALUES = By.cssSelector("div.bx-ljl-tier-values");

    private static final String REQUIRED_POINTS_INPUT_PATTERN = "input[id^='requirements-threshold-min-%s']";

    private static final String REQUIRED_CRITERIA_POINTS_INPUT_PATTERN = "input[id^='requirements-criteria-%s']";

    private static final String REWARD_SELECTOR_PATTERN = "div[id^='%s'] bx-dropdown";

    private static final String REWARD_POINTS_INPUT_PATTERN = "input[id^='reward-points-%s'][id$='-%s']";

    private static final String REWARD_AMOUNT_INPUT_PATTERN = "input[id^='reward-amount-%s'][id$='-%s']";

    private static final By REFRESH_BONUS_LINK = By.cssSelector("div.refresh");

    // Expiration Details
    private static final By EXPIRATION_DETAILS = By.cssSelector("div.bx-expiration-details");

    private static final By PERIOD_STARTING_DAY_SELECTOR = By.cssSelector("div.bx-expiration-details bx-dropdown");

    // Publishing Options
    private static final By PUBLISHING_OPTIONS = By.cssSelector("div.bx-ljl-publishing-options");

    private static final By SCHEDULE_BUTTON = By.id("publish-button");

    private static final By VALID_FROM_INPUT = By.id("day-picker-input");

    private static final By MODAL_DIALOG = By.cssSelector("bx-dialog");

    private static final By MODAL_DIALOG_OK_BUTTON = By.cssSelector("button.primary");

    private static final By STEP_ID_REGIONS = By.id("bx-nav-regions");

    private static final By STEP_ID_NAMES = By.id("bx-nav-names");

    private static final By STEP_ID_EARNING_POINTS = By.id("bx-nav-earningpoints");

    private static final By STEP_ID_CURRENCY_SETUP = By.id("bx-nav-currencysetup");

    private static final By STEP_ID_TIER_NAMES = By.id("bx-nav-tiernames");

    private static final By STEP_ID_TIER_VALUES = By.id("bx-nav-tiervalues");

    private static final By STEP_ID_EXPIRATION_DETAILS = By.id("bx-nav-expirationdetails");

    //Wizard Step List

    private static final By STEP_ID_PUBLISHING_OPTIONS = By.id("bx-nav-publishingoptions");

    static {
        CASINO_CATEGORIES.put("tablegame", 0);
        CASINO_CATEGORIES.put("videopoker", 1);
        CASINO_CATEGORIES.put("slotgame", 2);
        CASINO_CATEGORIES.put("specialtygame", 3);
    }

    @Autowired
    private BrowserManager browserManager;

    @Autowired
    private BrowserForm browserForm;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private VisualUtils visualUtils;

    @Autowired
    private DatePickerHelper datePickerHelper;

    @Autowired
    private Reporter reporter;

    public void fillWizard(final Program program) {
        fillWizard(program, false);
    }

    public List<ImageValidation> fillWizard(final Program program, final boolean takeScreenshots) {
        final List<ImageValidation> images = new ArrayList<>();

        // Markets
        fillRegion(program);

        if (takeScreenshots) {
            images.add(ImageValidation.builder().snapshot(visualUtils.takeSnapshot(getFileName("rm_wz_REGIONS"))).build());
        }

        saveAndContinueStep();

        // Names
        fillNames(program);

        if (takeScreenshots) {
            images.add(ImageValidation.builder().snapshot(visualUtils.takeSnapshot(getFileName("rm_wz_NAMES"))).build());
        }

        saveAndContinueStep();

        // Earning Points
        if (takeScreenshots) {
            images.add(ImageValidation.builder().snapshot(
                    visualUtils.takeSnapshot(getFileName("rm_wz_EARNING_POINTS_before"))).build());
        }

        fillEarningPoints(program);

        if (takeScreenshots) {
            images.add(ImageValidation.builder().snapshot(
                    visualUtils.takeSnapshot(getFileName("rm_wz_EARNING_POINTS_after"))).build());
        }

        saveAndContinueStep();

        // Currency Setup
        if (program.getCurrencies().size() > 1) {
            if (takeScreenshots) {
                images.add(ImageValidation.builder().snapshot(
                        visualUtils.takeSnapshot(getFileName("rm_wz_CURRENCY_SETUP_before"))).build());
            }

            fillCurrencySetup(program);

            if (takeScreenshots) {
                images.add(ImageValidation.builder().snapshot(
                        visualUtils.takeSnapshot(getFileName("rm_wz_CURRENCY_SETUP_after"))).build());
            }

            saveAndContinueStep();
        }

        // Tier Names
        fillTierNames(program);

        if (takeScreenshots) {
            images.add(ImageValidation.builder().snapshot(visualUtils.takeSnapshot(getFileName("rm_wz_TIER_NAMES"))).build());
        }

        saveAndContinueStep();

        // Tier Values
        fillTierValues(program);

        if (takeScreenshots) {
            refreshBonusList();
            images.add(ImageValidation.builder().snapshot(visualUtils.takeSnapshot(getFileName("rm_wz_TIER_VALUES"))).build());
        }

        saveAndContinueStep();

        // Expiration Details
        fillExpirationDetails(program);

        if (takeScreenshots) {
            images.add(ImageValidation.builder().snapshot(
                    visualUtils.takeSnapshot(getFileName("rm_wz_EXPIRATION_DETAILS"))).build());
        }

        saveAndContinueStep();

        // Publishing Options
        fillPublishingOptions(program);

        if (takeScreenshots) {
            images.add(ImageValidation.builder().snapshot(
                    visualUtils.takeSnapshot(getFileName("rm_wz_PUBLISHING_OPTIONS"))).build());
            discardProgram();
            images.add(
                    ImageValidation.builder().snapshot(visualUtils.takeSnapshot(getFileName("rm_wz_DISCARD_popup"))).build());
        } else {
            scheduleProgram();
        }

        return images;
    }

    public void editProgramInWizard(final Program program) {
        // Markets can not be modified
        saveAndContinueStep();
        // Names
        editNames(program);
        saveAndContinueStep();
        // Earning Points
        saveAndContinueStep();
        // Currency Setup
        if (program.getCurrencies().size() > 1) {
            saveAndContinueStep();
        }
        // Tier Names
        saveAndContinueStep();
        // Tier Values
        saveAndContinueStep();
        // Expiration Details
        saveAndContinueStep();
        // Publishing Options
        scheduleProgram();
    }

    public void saveAndContinueStep() {
        browserForm.clickOnElement(By.className("wizard-buttons"), SAVE_AND_CONTINUE_BUTTON);
    }

    public void clickSaveAtExceptionPopup() {
        browserManager.fluent().element(BUTTON_SAVE_EXCEPTION).click();
    }

    public void fillRegion(final Program program) {
        browserWait.waitForElement(REGIONS_STEP);
        final By marketRadioButton =
                By.cssSelector(format("label[for='program-regions-radio_%s']", program.getRegion().getCode()));
        browserForm.clickOnElement(REGIONS_STEP, marketRadioButton);
        reporter.info("REGION: " + program.getRegion());
    }

    public void fillNames(final Program program) {
        browserWait.waitForElement(TAB_CHILDREN_SELECTOR);

        final List<WebElement> tabs = browserManager.driver().findElements(TAB_CHILDREN_SELECTOR);
        final List<WebElement> inputs = browserManager.driver().findElements(NAMES_INPUT);

        for (int i = 0; i < tabs.size(); i++) {
            tabs.get(i).click();
            inputs.get(i).sendKeys(program.getNames().get("en"));
            reporter.info("NAME: " + program.getNames().get("en"));
        }
    }

    public void editNames(final Program program) {
        browserWait.waitForElement(TAB_CHILDREN_SELECTOR);

        final List<WebElement> tabs = browserManager.driver().findElements(TAB_CHILDREN_SELECTOR);
        final List<WebElement> inputs = browserManager.driver().findElements(NAMES_INPUT);

        for (int i = 0; i < tabs.size(); i++) {
            tabs.get(i).click();
            inputs.get(i).sendKeys(inputs.get(i).getText() + EDITION_ADD_TEXT);
        }
    }

    public void looseFocus() {
        browserForm.clickOnElement(MAIN_CONTAINER, STEP_TITLE);
    }

    public void setProgramName(final String name) {
        browserWait.waitForElement(TAB_CHILDREN_SELECTOR);
        browserForm.fillTextField(NAMES_INPUT, name);
    }

    public void setRewardName(final String name) {
        browserWait.waitForElement(TAB_CHILDREN_SELECTOR);
        browserForm.fillTextField(REWARD_NAMES_INPUT, name);
    }

    private String fillEarningPoints(final Program program) {
        browserWait.waitForElement(EARNING_POINTS);

        final String currency = program.getCurrencies().size() == 1 ?
                program.getCurrencies().get(0) :
                browserManager.driver().findElement(SELECTED_CURRENCY).getAttribute("value").trim();

        program.getContributions().forEach(contribution -> {

            if (contribution.getTokenType().equals(CATEGORY)) {

                final Integer index = Integer.valueOf(contribution.getToken()) - 1;

                fillCategoryContribution(contribution, currency, index);

            } else if (contribution.getTokenType().equals(TokenType.GAME)) {

                final String categoryKey = transformCategory(contribution.getParent());
                final Integer index = CASINO_CATEGORIES.get(categoryKey);

                fillGameContribution(contribution, currency, index);
            }
        });

        return currency;
    }

    public void clickAddExceptionButton() {
        browserManager.fluent().element(CONTRIBUTION_BUTTON).click();
    }

    private void fillCurrencySetup(final Program program) {
        browserWait.waitForElement(CURRENCY_SETUP);

        browserManager.driver().findElements(CURRENCY_SETUP_TABS).forEach(tab -> {

            final String currency = tab.getText().trim();
            tab.click();

            program.getContributions().forEach(contribution -> {

                if (contribution.getTokenType().equals(CATEGORY)) {

                    final Integer index = Integer.valueOf(contribution.getToken()) - 1;
                    final String product = contribution.getParent();
                    final String type = contribution.getType().name().toLowerCase();
                    final String amountId = format(CONTRIBUTION_AMOUNT_PATTERN, product, type, index, currency);

                    browserForm.fillTextField(By.id(amountId), contribution.getRatios().get(currency).getAmount().toString());

                } else if (contribution.getTokenType().equals(TokenType.GAME)) {

                    final String amountId = format(EXCEPTION_AMOUNT_PATTERN, contribution.getToken(), currency);
                    browserForm.fillTextField(By.id(amountId), contribution.getRatios().get(currency).getAmount().toString());
                }
            });
        });
    }

    private void fillTierNames(final Program program) {
        browserWait.waitForElement(TIER_NAMES);

        final List<Tier> tiers = program.getTiers();
        final List<WebElement> tabs = browserManager.driver().findElements(TIER_NAMES_TABS);
        final int tiersNumber = tiers.size();

        for (int i = 0; i < tiersNumber - 1; i++) {
            browserForm.clickOnElement(TIER_NAMES, TIER_NAMES_ADD_BUTTON);
        }

        final List<WebElement> inputs = browserManager.driver().findElements(TIER_NAMES_INPUTS);

        for (int i = 0; i < tabs.size(); i++) {

            final WebElement tab = tabs.get(i);
            final String language = tab.getText().trim();

            tab.click();

            for (int j = 0; j < tiersNumber; j++) {
                inputs.get(tiersNumber * i + j).sendKeys(format("%s %s", program.getTiers().get(j).getNames().get("en"), language));
            }

        }
    }

    private void fillTierValues(final Program program) {
        browserWait.waitForElement(TIER_VALUES);

        program.getTiers().forEach(tier -> {

            final String tierNameAsId = tierNameAsId(tier);

            if (!isFirstTier(tier)) {

                final By requiredPoints = By.cssSelector(format(REQUIRED_POINTS_INPUT_PATTERN, tierNameAsId));
                browserForm.fillTextField(requiredPoints, String.valueOf(tier.getRequiredPoints()));

                final By requiredCriteriaPoints =
                        By.cssSelector(format(REQUIRED_CRITERIA_POINTS_INPUT_PATTERN, tierNameAsId));
                browserForm.fillTextField(requiredCriteriaPoints, String.valueOf(tier.getRequiredCriteriaPoints()));

            }

            browserForm.selectValueOnSelector(By.cssSelector(format(REWARD_SELECTOR_PATTERN, tierNameAsId)), tier.getRewardName(), false);

            tier.getPointsToCash().forEach((currency, ratio) -> {

                final By points = By.cssSelector(format(REWARD_POINTS_INPUT_PATTERN, tierNameAsId, currency));
                browserForm.fillTextField(points, ratio.getPoints().toString());

                final By amount = By.cssSelector(format(REWARD_AMOUNT_INPUT_PATTERN, tierNameAsId, currency));
                browserForm.fillTextField(amount, ratio.getAmount().toString());

            });

        });
    }

    private void refreshBonusList() {
        browserManager.fluent().element(REFRESH_BONUS_LINK).click();
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fillExpirationDetails(final Program program) {
        browserWait.waitForElement(EXPIRATION_DETAILS);

        browserForm.fillTextField(REWARD_POINTS_LIFESPAN, program.getRewardPointsLifespan().toString());
        browserForm.selectValueOnSelector(PERIOD_STARTING_DAY_SELECTOR, program.getPeriodStartingDay().toString(), false);
    }

    public void fillPublishingOptions(final Program program) {
        browserWait.waitForElement(PUBLISHING_OPTIONS);

        if (program.getValidFrom() != null) {
            datePickerHelper.selectDate(program.getValidFrom());
        }

        if (StringUtils.isNotBlank(program.getLink())) {
            browserForm.fillTextField(LINK_INPUT, program.getLink());
        }
    }

    public void scheduleProgram() {
        browserForm.clickOnElement(PUBLISHING_OPTIONS, SCHEDULE_BUTTON);
    }

    public void discardProgram() {
        browserManager.fluent().element(DISCARD_BUTTON).click();
    }

    private void fillCategoryContribution(final Contribution contribution, final String currency, final Integer index) {
        final String product = contribution.getParent();
        final String type = contribution.getType().name().toLowerCase();
        final String pointsId = format(CONTRIBUTION_POINTS_PATTERN, product, type, index, currency);
        final String amountId = format(CONTRIBUTION_AMOUNT_PATTERN, product, type, index, currency);

        browserForm.fillTextField(By.id(pointsId), contribution.getRatios().get(currency).getPoints().toString());
        browserForm.fillTextField(By.id(amountId), contribution.getRatios().get(currency).getAmount().toString());
    }

    private void fillGameContribution(final Contribution contribution, final String currency, final Integer index) {

        browserForm.clickOnElement(CASINO_EARNING_POINTS, By.id(format(CONTRIBUTION_BUTTON_PATTERN, index)));

        final String pointsId = format(GAME_POINTS_PATTERN, contribution.getToken());
        final String amountId = format(GAME_AMOUNT_PATTERN, contribution.getToken());

        browserForm.fillTextField(By.id(pointsId), contribution.getRatios().get(currency).getPoints().toString());
        browserForm.fillTextField(By.id(amountId), contribution.getRatios().get(currency).getAmount().toString());
        browserForm.clickOnElement(MODAL_CONTENT, BUTTON_SAVE_EXCEPTION);
    }

    private String transformCategory(final String category) {
        return category.replace(" ", "").trim().toLowerCase();
    }

    private boolean isFirstTier(final Tier tier) {
        return tier.getRequiredPoints() == 0;
    }

    private String tierNameAsId(final Tier tier) {
        return tier.getNames().get("en").toLowerCase().replaceAll(" ", "-");
    }

    public void navigateToStepName(final String stepName) {

        By idToNavigate = null;

        if (stepName.equals("REGIONS")) {
            idToNavigate = STEP_ID_REGIONS;
        } else if (stepName.equals("NAMES")) {
            idToNavigate = STEP_ID_NAMES;
        } else if (stepName.equals("EARNING POINTS")) {
            idToNavigate = STEP_ID_EARNING_POINTS;
        } else if (stepName.equals("CURRENCY SETUP")) {
            idToNavigate = STEP_ID_CURRENCY_SETUP;
        } else if (stepName.equals("TIER NAMES")) {
            idToNavigate = STEP_ID_TIER_NAMES;
        } else if (stepName.equals("TIER VALUES")) {
            idToNavigate = STEP_ID_TIER_VALUES;
        } else if (stepName.equals("EXPIRATION DETAILS")) {
            idToNavigate = STEP_ID_EXPIRATION_DETAILS;
        } else if (stepName.equals("PUBLISHING OPTIONS")) {
            idToNavigate = STEP_ID_PUBLISHING_OPTIONS;
        }

        if (idToNavigate != null) {
            browserManager.fluent().element(idToNavigate).click();
        } else {
            reporter.info("Unknown Step name.");
        }
    }

}
