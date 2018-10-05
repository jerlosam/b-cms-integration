package io.crazy88.beatrix.e2e.visual;

import static io.crazy88.beatrix.e2e.visual.VisualSteps.IMAGES_TO_COMPARE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.qaauto.picasso.java.client.core.dto.PicassoRectangle;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.bulkchangemanager.actions.BulkChangeManagerActions;
import io.crazy88.beatrix.e2e.bulkchangemanager.dto.JobType;

@Component
public class VisualBulkChangeManagerSteps {

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private VisualUtils visualUtils;

    @Autowired
    private BulkChangeManagerActions bulkChangeManagerActions;

    @When("a customer manager covers main flow of create Force Phone Update job")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversForcePhoneUpdateJob() {
        List<ImageValidation> images = new ArrayList<>();

        bulkChangeManagerActions.login();

        browserWait.waitForElement(By.className("bx-user-profile"));

        browserWait.waitForElement(By.tagName("alice-brand-select"));

        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("bulkChangeManager-AliceTypeOfJob")).build());

        bulkChangeManagerActions.selectJobType(JobType.FORCE_PHONE_UPDATE);
        
        browserWait.waitForElement(By.className("alice-brand"));

        PicassoRectangle rectangle = visualUtils.getRectangle(By.className("bx-panel-header"));

        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage("bulkChangeManager-ForcePhoneUpdate-Region")).ignoredRegionList(
                Arrays.asList(rectangle)).build());

        bulkChangeManagerActions.selectRegion();

        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage("bulkChangeManager-ForcePhoneUpdate-InternalMessage")).ignoredRegionList(
                Arrays.asList(rectangle)).build());

        bulkChangeManagerActions.addInternalMessage("Accounts", "Internal Note");

        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage("bulkChangeManager-ForcePhoneUpdate-DateAndTime")).ignoredRegionList(
                Arrays.asList(rectangle)).build());

        bulkChangeManagerActions.selectDateAndTime();

        PicassoRectangle jobNameRectangle = visualUtils.getRectangle(By.id("jobNames-jobName"));

        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage("bulkChangeManager-ForcePhoneUpdate-NameAndDescription")).ignoredRegionList(
                Arrays.asList(rectangle, jobNameRectangle)).build());

        bulkChangeManagerActions.enterNameAndDescription("JOB NAME", "JOB DESCRIPTION");

        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage("bulkChangeManager-ForcePhoneUpdate-UploadFILE")).ignoredRegionList(Arrays.asList(rectangle)).build());


        return images;
    }

    @When("a customer manager covers main flow of create Bulk Account Entry job")
    @ToContext(IMAGES_TO_COMPARE)
    public List<ImageValidation> whenCoversBulkAccountEntryJob() {
        List<ImageValidation> images = new ArrayList<>();

        bulkChangeManagerActions.login();

        browserWait.waitForElement(By.className("bx-user-profile"));

        bulkChangeManagerActions.selectBrand();

        images.add(ImageValidation.builder().snapshot(visualUtils.captureImage("bulkChangeManager-BeatrixTypeOfJob")).build());

        bulkChangeManagerActions.selectJobType(JobType.BULK_ACCOUNT_ENTRY);

        browserWait.waitForElement(By.className("beatrix-brand"));

        PicassoRectangle rectangle = visualUtils.getRectangle(By.className("bx-panel-header"));

        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage("bulkChangeManager-BulkAccountEntry-DateAndTime")).ignoredRegionList(
                Arrays.asList(rectangle)).build());

        bulkChangeManagerActions.selectDateAndTime();

        PicassoRectangle jobNameRectangle = visualUtils.getRectangle(By.id("jobNames-jobName"));

        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage("bulkChangeManager-BulkAccountEntry-NameAndDescription")).ignoredRegionList(
                Arrays.asList(rectangle, jobNameRectangle)).build());

        bulkChangeManagerActions.enterNameAndDescription("JOB NAME", "JOB DESCRIPTION");

        images.add(ImageValidation.builder().snapshot(
                visualUtils.captureImage("bulkChangeManager-BulkAccountEntry-UploadFILE")).ignoredRegionList(Arrays.asList(rectangle)).build());

        return images;
    }

}
