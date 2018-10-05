package io.crazy88.beatrix.e2e.visual;

import internal.qaauto.picasso.java.client.core.dto.PicassoRectangle;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.bonus.BonusTestActions;
import io.crazy88.beatrix.e2e.bonus.actions.BonusActions;
import io.crazy88.beatrix.e2e.bonus.dto.Bonus;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.rewardmanager.actions.RewardManagerActions;
import io.crazy88.beatrix.e2e.rewardmanager.actions.TemplateActions;
import io.crazy88.beatrix.e2e.rewardmanager.bonus.components.BonusDashboardComponent;
import io.crazy88.beatrix.e2e.rewardmanager.bonus.components.BonusEditionComponent;
import io.crazy88.beatrix.e2e.rewardmanager.bonus.components.BonusHeaderComponent;
import io.crazy88.beatrix.e2e.rewardmanager.bonus.components.SettingsComponent;
import io.crazy88.beatrix.e2e.rewardmanager.bonus.components.TemplateEditionComponent;
import io.crazy88.beatrix.e2e.rewardmanager.dto.Template;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.actions.VisualProgramActions;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.factory.ProgramFactory;

import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static io.crazy88.beatrix.e2e.visual.VisualSteps.IMAGES_TO_COMPARE;

@Component
public class VisualRewardManagerSteps {

    private static final String IMAGE_PREFIX = "rm_";

    @Autowired
    private VisualUtils visualUtils;

    @Autowired
    private RewardManagerActions rewardManagerActions;

    @Autowired
    private BonusActions bonusActions;

    @Autowired
    private BonusTestActions bonusTestActions;

    @Autowired
    private BonusDashboardComponent bonusDashboard;

    @Autowired
    private BonusEditionComponent bonusEdition;

    @Autowired
    private BonusHeaderComponent header;

    @Autowired
    private TemplateActions templateActions;

    @Autowired
    private SettingsComponent settings;

    @Autowired
    private TemplateEditionComponent templateEdition;

    @Autowired
    private E2EProperties e2EProperties;

    @Autowired
    private ProgramFactory programFactory;

    @Autowired
    private VisualProgramActions visualProgramActions;

    @Autowired
    private BrowserWait browserWaits;

    @When("a reward manager covers main flows of wizard")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversWizard() {
        List<ImageValidation> images = new ArrayList<>();

        String currency = bonusTestActions.getCurrencyFromBrandProperties(e2EProperties.getBrandCode());

        Bonus bonus = bonusActions.generateVisualBonusDataByCurrency(currency);

        rewardManagerActions.enterToRewardsManagerHomePage();
        bonusDashboard.selectBrand(e2EProperties.getBrandName());
        List<PicassoRectangle> ignoreBonusCodeRegion = new ArrayList<>();

        browserWaits.waitForPageToBeLoadedCompletely();

        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "home", BonusEditionComponent.WIZARD)).build());

        bonusDashboard.navigateToCreateFirstTimeDeposit();
        bonusEdition.fillRegionsStep(bonus);

        ignoreBonusCodeRegion.add(visualUtils.getRectangle(BonusEditionComponent.INTERNAL_CODE));
        images.add(
                ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "wizardRegions")).ignoredRegionList(
                        ignoreBonusCodeRegion).tolerance(0.7).build());

        bonusEdition.saveAndContinueStep();
        bonusEdition.fillDatesStep(bonus);
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "wizardDates")).ignoredRegionList(
                ignoreBonusCodeRegion).build());

        bonusEdition.saveAndContinueStep();
        bonusEdition.fillDepositDetailsStep(bonus);
        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage(IMAGE_PREFIX + "wizardDepositDetails")).ignoredRegionList(
                ignoreBonusCodeRegion).build());

        bonusEdition.saveAndContinueStep();
        bonusEdition.fillRewardStepWithCurrency(bonus, currency);
        images.add(
                ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "wizardReward")).ignoredRegionList(
                        ignoreBonusCodeRegion).build());

        bonusEdition.saveAndContinueStep();
        bonusEdition.fillRulesStep(bonus);
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "wizardRules")).ignoredRegionList(
                ignoreBonusCodeRegion).build());

        bonusEdition.saveAndContinueStep();
        bonusEdition.fillNamesStep(bonus);
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "wizardNames")).ignoredRegionList(
                ignoreBonusCodeRegion).build());

        bonusEdition.saveAndContinueStep();
        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage(IMAGE_PREFIX + "wizardDistribution")).ignoredRegionList(
                ignoreBonusCodeRegion).build());

        bonusEdition.discardForm();
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "wizardAlert")).ignoredRegionList(
                ignoreBonusCodeRegion).build());

        return images;
    }

    @When("a reward manager covers main flows of templates")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversTemplates() {
        List<ImageValidation> images = new ArrayList<>();

        Template template = templateActions.generateVisualTemplateData();

        rewardManagerActions.enterToRewardsManagerHomePage();
        bonusDashboard.selectBrand(e2EProperties.getBrandName());
        header.navigateToSettingsTab();
        settings.newTemplate();
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "newTemplate")).build());

        templateEdition.fillTemplate(template);
        templateEdition.addGameException();
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "templateExceptions")).build());

        templateEdition.selectGameException();
        images.add(
                ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "templateWithExceptions")).build());

        templateEdition.discardTemplate();
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "templateAlert")).build());

        return images;
    }

    @When("a rewards manager user inspects Creation wizard flow")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversProgramWizard() {
        return visualProgramActions.createProgram(programFactory.createProgram().withValidFrom(Instant.parse("2020-12-31T00:00:00.000Z")));
    }
}
