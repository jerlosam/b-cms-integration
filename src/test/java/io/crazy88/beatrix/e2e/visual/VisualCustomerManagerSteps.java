package io.crazy88.beatrix.e2e.visual;

import static io.crazy88.beatrix.e2e.visual.VisualSteps.IMAGES_TO_COMPARE;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.qaauto.picasso.java.client.core.dto.PicassoRectangle;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;
import io.crazy88.beatrix.e2e.customermanager.components.PlayerDetailsComponent;
import io.crazy88.beatrix.e2e.customermanager.components.PlayerMessageComponent;
import io.crazy88.beatrix.e2e.rewardmanager.bonus.components.BonusEditionComponent;

@Component
public class VisualCustomerManagerSteps {

    private static final String TEST_PLAYER = "visualtesting@crazy88.io";

    private static final String TEST_PLAYER_WITH_REFERRAL = "visualtestreferral@4null.com";

    private static final String NOT_EXISTING_EMAIL = "notexistingemail@crazy88.io";

    private static final String IMAGE_PREFIX = "cm_";

    private static final By OVERLAY_ELEMENT = By.cssSelector(".modal-content");
    
    private static final By HEADER = By.cssSelector("div.navigation-content");
    
    @Autowired
    private BrowserForm browserForm;

    @Autowired
    private VisualUtils visualUtils;

    @Autowired
    private CustomerManagerActions customerManagerActions;

    @Autowired
    private PlayerDetailsComponent playerDetailsComponent;

    @When("a customer manager covers main flows of player profile")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversHomePage() {
        List<ImageValidation> images = new ArrayList<>();

        customerManagerActions.enterToCustomerManagerHomePage();
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "home")).build());

        customerManagerActions.searchByEmail(NOT_EXISTING_EMAIL);
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "searchError")).build());

        customerManagerActions.searchByEmail(TEST_PLAYER);
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "searchPlayerResults")).build());

        customerManagerActions.navigateToPlayerFromSearchResults(TEST_PLAYER);
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "playerDetails")).build());

        playerDetailsComponent.navigateToUpdatePlayerDetails();
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "updateProfile")).build());

        return images;
    }

    @When("a customer manager covers main flows of player reward exclusions")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversRewardExclusions() {
        List<ImageValidation> images = new ArrayList<>();
        List<PicassoRectangle> ignoreHeaderRegion = new ArrayList<>();

        customerManagerActions.enterToCustomerManagerHomePage();
        customerManagerActions.searchByEmail(TEST_PLAYER);
        customerManagerActions.navigateToPlayerFromSearchResults(TEST_PLAYER);
        customerManagerActions.navigateToRewardExclusions();

        ignoreHeaderRegion.add(visualUtils.getRectangle(HEADER));
        
        images.add(ImageValidation.builder()
                .snapshot(visualUtils.captureImage(IMAGE_PREFIX + "playerRewardExclusions"))
                .ignoredRegionList(ignoreHeaderRegion)
                .build());

        return images;
    }

    @When("a customer manager covers main flows of player message")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversPlayerMessage() {
        List<ImageValidation> images = new ArrayList<>();

        customerManagerActions.enterToCustomerManagerHomePage();
        customerManagerActions.searchByEmail(TEST_PLAYER);
        customerManagerActions.navigateToPlayerFromSearchResults(TEST_PLAYER);
        playerDetailsComponent.navigateToPlayerMessage();

        images.add(ImageValidation.builder()
                .snapshot(visualUtils.captureImage(IMAGE_PREFIX + "playerMessage", OVERLAY_ELEMENT)).build());

        browserForm.submitForm(PlayerMessageComponent.SEND_BUTTON);

        images.add(ImageValidation.builder()
                .snapshot(visualUtils.captureImage(IMAGE_PREFIX + "playerMessageError",OVERLAY_ELEMENT)).build());

        return images;
    }
    
    @When("a customer manager covers main flows of forbidden")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversForbidden() {
        List<ImageValidation> images = new ArrayList<>();

        customerManagerActions.enterToForbiddenPage();

        images.add(ImageValidation.builder()
                .snapshot(visualUtils.captureImage(IMAGE_PREFIX + "forbbidenPage")).build());

        return images;
    }

    @When("a customer manager covers main flows of player related accounts")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversPlayerRelatedAccounts() {

        enterCMAndSearchAndNavigateToPlayer(TEST_PLAYER_WITH_REFERRAL);

        List<ImageValidation> images = new ArrayList<>();

        if (customerManagerActions.isRelatedAccountAffiliateRelationDisplayed()) {
            images.add(ImageValidation.builder()
                    .snapshot(visualUtils.captureImage(IMAGE_PREFIX + "relatedReferralAccount")).build());

        }
        return images;

    }

    private void enterCMAndSearchAndNavigateToPlayer(String playerEmail){
        customerManagerActions.enterToCustomerManagerHomePage();
        customerManagerActions.searchByEmail(playerEmail);

    }
}
