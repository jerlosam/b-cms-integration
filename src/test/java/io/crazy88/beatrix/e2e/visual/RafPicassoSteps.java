package io.crazy88.beatrix.e2e.visual;

import internal.qaauto.picasso.java.client.core.PicassoConfiguration;
import internal.qaauto.picasso.java.client.core.exceptions.PicassoException;
import io.crazy88.beatrix.e2e.tools.RAFEnable;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static io.crazy88.beatrix.e2e.visual.VisualSteps.IMAGES_TO_COMPARE;

@Component
public class RafPicassoSteps extends PicassoSteps {

    @Autowired
    public RafPicassoSteps(PicassoConfiguration picassoConfiguration) {
        super(picassoConfiguration);
    }

    @Override
    @RAFEnable
    @Then("check that RAF UX is correct")
    public void checkUX(@FromContext(IMAGES_TO_COMPARE) List<ImageValidation> images) throws IOException, PicassoException {
        super.checkUX(images);
    }

}
