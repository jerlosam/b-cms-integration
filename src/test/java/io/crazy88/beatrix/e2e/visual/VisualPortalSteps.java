package io.crazy88.beatrix.e2e.visual;

import static io.crazy88.beatrix.e2e.visual.VisualSteps.IMAGES_TO_COMPARE;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.portal.PortalActions;

@Component
public class VisualPortalSteps {

    private static final String IMAGE_PREFIX = "portal_";

    @Autowired
    private VisualUtils visualUtils;

    @Autowired
    private PortalActions portalActions;

    @When("an enterprise user covers main flows of portal")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversPortalFlow() {
        List<ImageValidation> images = new ArrayList<>();

        portalActions.enterToPortalHomePage();
        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage(IMAGE_PREFIX + "home")).build());

        return images;
    }
}
