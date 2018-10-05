package io.crazy88.beatrix.e2e.visual;

import static io.crazy88.beatrix.e2e.visual.VisualSteps.IMAGES_TO_COMPARE;

import java.util.ArrayList;
import java.util.List;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.qaauto.picasso.java.client.core.dto.PicassoRectangle;
import io.crazy88.beatrix.e2e.player.actions.ContactUsActions;
import io.crazy88.beatrix.e2e.player.actions.ReactiveChatActions;
import io.crazy88.beatrix.e2e.tools.ReactiveChatEnable;

@Component
public class VisualChatSteps {

    private static final By OVERLAY_ELEMENT = By.tagName("bx-overlay-container");

    private static final By REACTIVE_CHAT_FRAME = By.id("MoxieFlyoutPanel");

    @Autowired
    private VisualUtils visualUtils;

    @Autowired
    private ContactUsActions contactUsActions;

    @Autowired
    private ReactiveChatActions reactiveChatActions;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserManager browser;

    private void stopBannerCarousel(boolean firstSlide) {
        if (firstSlide) {
            browserWait.waitForElementPresent(By.cssSelector(".carousel-indicators li"));
            ((JavascriptExecutor) browser.driver()).executeScript("document.querySelector('.carousel-indicators li').click();");
        }
        ((JavascriptExecutor) browser.driver()).executeScript(
                "var interval=__zone_symbol__setInterval(function(){},10000); for(var i=0; i<=interval; i++){clearInterval(i);}");
    }

    @ReactiveChatEnable
    @When("a player covers main flows of access to Reactive Chat in Contact Us page")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversReactiveChatFromContactUs() {
        List<ImageValidation> images = new ArrayList<>();
        List<PicassoRectangle> ignoreChatRegion = new ArrayList<>();

        contactUsActions.navigateToContactUsPage();
        stopBannerCarousel(true);

        images.add(ImageValidation.builder()
                .snapshot(visualUtils.captureImage("playerContactUs", OVERLAY_ELEMENT))
                .build());

        // temporary fix for the problem caused by KIDDO-4204 (forcing focus loss)
        contactUsActions.clickOnEmailField();
        contactUsActions.clickOnHeader();

        reactiveChatActions.clickOnChatNowLink();
        browserWait.waitForElement(REACTIVE_CHAT_FRAME);
        ignoreChatRegion.add(visualUtils.getRectangle(REACTIVE_CHAT_FRAME));

        images.add(ImageValidation.builder()
                .snapshot(visualUtils.captureImage("playerContactUsReactiveChat"))
                .ignoredRegionList(ignoreChatRegion)
                .tolerance(0.3)
                .build());

        return images;
    }
}
