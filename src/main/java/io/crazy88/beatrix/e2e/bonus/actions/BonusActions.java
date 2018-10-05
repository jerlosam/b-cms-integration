package io.crazy88.beatrix.e2e.bonus.actions;

import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.bonus.dto.Bonus;
import io.crazy88.beatrix.e2e.player.actions.PlayerAccountMenuActions;
import io.crazy88.beatrix.e2e.player.components.BonusAvailableComponent;
import io.crazy88.beatrix.e2e.rewardmanager.actions.RewardManagerActions;
import io.crazy88.beatrix.e2e.rewardmanager.bonus.components.BonusDashboardComponent;
import io.crazy88.beatrix.e2e.rewardmanager.bonus.components.BonusEditionComponent;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Component
public class BonusActions {

    @Autowired
    private BonusEditionComponent bonusEdition;

    @Autowired
    private BonusDashboardComponent bonusDashboard;

    @Autowired
    private BonusAvailableComponent bonusAvailable;

    @Autowired
    private RewardManagerActions rewardManagerActions;

    @Autowired
    private PlayerAccountMenuActions playerAccountMenuActions;

    @Autowired
    private E2EProperties e2EProperties;


    public String createDraft(Bonus bonusDraft, String site, String currency){
        rewardManagerActions.enterToRewardsManagerHomePage();
        bonusDashboard.selectBrand(site);
        bonusDashboard.navigateToCreateFirstTimeDeposit();
        String internalCode = bonusEdition.fillWizardWithCurrency(bonusDraft, currency);
        bonusEdition.saveAsDraft();
        return internalCode;
    }

    public void claimFirstUpfrontBonus(){
        playerAccountMenuActions.navigateToAvailableBonuses();
        bonusAvailable.claimFirstUpfrontBonus();
    }

    public void forfeitFirstActiveBonus(){
        playerAccountMenuActions.navigateToAvailableBonuses();
        bonusAvailable.forfeitBonus();
    }

    public Bonus generateRandomBonusDataByCurrency(String currency) {
        Map<String, BigDecimal> amounts = new HashMap<>();

        String codeOne = RandomStringUtils.randomAlphabetic(20);
        String codeTwo = RandomStringUtils.randomAlphabetic(20);

        amounts.put(currency, new BigDecimal("15.50"));
        String randomCode = randomNumeric(5);
        Bonus bonus = Bonus.builder()
                .internalName(String.format("E2E - Int - %s", randomCode))
                .externalName(String.format("E2E - Ext - %s", randomCode))
                .codes(new ArrayList<>(Arrays.asList(codeOne, codeTwo)))
                .description(String.format("E2E - Description - %s", randomCode))
                .startDate(getFormattedDate(LocalDateTime.now().minusDays(1)))
                .endDate(getFormattedDate(LocalDateTime.now().plusDays(1)))
                .amounts(amounts)
                .factor(new BigDecimal(2))
                .rolloverBase(0)
                .lockAmount(new BigDecimal("20"))
                .bonusPriority(0)
                .build();

        LocalDate.now().atStartOfDay().format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
        return bonus;
    }

    public Bonus generateVisualBonusDataByCurrency(String currency) {
        Map<String, BigDecimal> amounts = new HashMap<>();
        amounts.put(currency, new BigDecimal("15.50"));
        return Bonus.builder()
                .internalName("MyVisualTestingBonusInternalName")
                .externalName("MyVisualTestingBonusPublicName")
                .description("Description visual test")
                .startDate("04-11-2000 11:22")
                .endDate("11-04-2020 22:11")
                .daysAfterRedemption("10")
                .expiredDate("")
                .amounts(amounts)
                .factor(new BigDecimal(2))
                .rolloverBase(0)
                .lockAmount(new BigDecimal("20"))
                .templateName("VisualTesting Template")
                .bonusPriority(0)
                .build();
    }

    public boolean isBonusDisplayedOnTheDashboard(Bonus bonus){
        return bonusDashboard.isBonusDisplayed(bonus);
    }

    public boolean isDraftDisplayedOnTheDashboard(Bonus bonusDraft){
        return bonusDashboard.isDraftDisplayed(bonusDraft);
    }

    private String getFormattedDate(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
    }

    public void deleteBonus(Bonus bonus) {
        bonusDashboard.deleteBonus(bonus);
    }

    public String editDraftAndConvertToBonus(Bonus bonusDraft) {
        bonusDashboard.editDraft(bonusDraft);
        String internalCode = bonusEdition.saveAndContinueAllSteps(bonusDraft);
        bonusEdition.submitForm();
        return internalCode;
    }
    public String editBonus(Bonus bonus) {
        bonusDashboard.editBonus(bonus);
        String internalCode = bonusEdition.saveAndContinueAllSteps(bonus);
        bonusEdition.submitForm();
        return internalCode;
    }
}
