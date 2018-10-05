package io.crazy88.beatrix.e2e.rewardmanager.loyalty.actions;

import static io.crazy88.beatrix.e2e.rewardmanager.loyalty.utils.FileUtils.getFileName;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.rewardmanager.actions.RewardManagerActions;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.components.ExistingProgramsComponent;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.components.LoyaltyHeaderComponent;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.components.ProgramComponent;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.components.WizardComponent;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Program;
import io.crazy88.beatrix.e2e.visual.ImageValidation;
import io.crazy88.beatrix.e2e.visual.VisualUtils;

@Component
public class VisualProgramActions {

    @Autowired
    private LoyaltyHeaderComponent loyaltyHeaderComponent;

    @Autowired
    private ProgramComponent programComponent;

    @Autowired
    private RewardManagerActions rewardManagerActions;

    @Autowired
    private VisualUtils visualUtils;

    @Autowired
    private WizardComponent wizardComponent;

    public List<ImageValidation> createProgram(final Program program) {
        final List<ImageValidation> images = new ArrayList<>();

        rewardManagerActions.enterToRewardsManagerLoyaltySection();

        images.add(ImageValidation.builder()
                .snapshot(visualUtils.captureImage(getFileName("rm_LANDING_PAGE_after_login"),
                        ExistingProgramsComponent.CONTENT))
                .build());

        loyaltyHeaderComponent.selectBrand(program.getBrandForTesting().getLabel());

        programComponent.createNewProgram();

        images.addAll(wizardComponent.fillWizard(program, true));

        return images;
    }
}
