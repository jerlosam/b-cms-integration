package io.crazy88.beatrix.e2e.rewardmanager.loyalty.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.rewardmanager.actions.RewardManagerActions;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.components.ExistingProgramsComponent;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.components.LoyaltyHeaderComponent;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.components.ProgramComponent;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.components.WizardComponent;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Program;

@Component
public class ProgramActions {

    @Autowired
    private ExistingProgramsComponent existingProgramsComponent;

    @Autowired
    private RewardManagerActions rewardManagerActions;

    @Autowired
    private LoyaltyHeaderComponent loyaltyHeaderComponent;

    @Autowired
    private ProgramComponent programComponent;

    @Autowired
    private WizardComponent wizardComponent;

    public void createProgram(final Program program) {
        rewardManagerActions.enterToRewardsManagerLoyaltySection();
        loyaltyHeaderComponent.selectBrand(program.getBrandForTesting().getLabel());
        programComponent.createNewProgram();
        wizardComponent.fillWizard(program);
    }

    public void editProgram(final Program program) {
        rewardManagerActions.enterToRewardsManagerLoyaltySection();
        loyaltyHeaderComponent.selectBrand(program.getBrandForTesting().getLabel());
        loyaltyHeaderComponent.navigateToExistingPrograms();
        existingProgramsComponent.editProgram(program.getId().toString());
        wizardComponent.editProgramInWizard(program);
    }

    public String getProgramId(final Program program) {
        return existingProgramsComponent.getProgramId(program);
    }

    public boolean isProgramDisplayed(final Program program, final boolean afterEdition) {
        return existingProgramsComponent.isProgramDisplayed(program, afterEdition);
    }
}
