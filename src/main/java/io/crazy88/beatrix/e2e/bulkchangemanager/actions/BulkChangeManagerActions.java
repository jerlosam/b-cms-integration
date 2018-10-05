package io.crazy88.beatrix.e2e.bulkchangemanager.actions;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.AliceBackofficeProperties;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.bulkchangemanager.components.BackofficeAccountInfoComponent;
import io.crazy88.beatrix.e2e.bulkchangemanager.components.BulkLoginComponent;
import io.crazy88.beatrix.e2e.bulkchangemanager.components.DateAndTimeComponent;
import io.crazy88.beatrix.e2e.bulkchangemanager.components.InternalMessageComponent;
import io.crazy88.beatrix.e2e.bulkchangemanager.dto.JobType;
import io.crazy88.beatrix.e2e.bulkchangemanager.components.JobsListComponent;
import io.crazy88.beatrix.e2e.bulkchangemanager.components.NameAndDescriptionComponent;
import io.crazy88.beatrix.e2e.bulkchangemanager.components.NewJobComponent;
import io.crazy88.beatrix.e2e.bulkchangemanager.components.RegionsComponent;
import io.crazy88.beatrix.e2e.bulkchangemanager.components.UploadFileComponent;

@Component
public class BulkChangeManagerActions {

    @Autowired
    protected E2EProperties properties;
    
    @Autowired
    protected AliceBackofficeProperties aliceBackofficeProperties;

    @Autowired
    private BulkLoginComponent loginComponent;

    @Autowired
    private NewJobComponent newJobComponent;

    @Autowired
    private RegionsComponent regionsComponent;

    @Autowired
    private InternalMessageComponent internalMessageComponent;

    @Autowired
    private DateAndTimeComponent dateAndTimeComponent;

    @Autowired
    private NameAndDescriptionComponent nameAndDescriptionComponent;

    @Autowired
    private UploadFileComponent uploadFileComponent;

    @Autowired
    private JobsListComponent jobsListComponent;
    
    @Autowired
    private BackofficeAccountInfoComponent backofficeAccountInfoComponent;

    @Autowired
    protected BrowserNavigation browserNavigation;

    public void login() {
        browserNavigation.navigateToUrlWithRedirection(properties.getBulkChangeManagerHomeUrl(), "login?returnUrl=%2Fjobs%2Fnew");
        loginComponent.login(properties.getQaLdapUser(), properties.getQaLdapPassword());
    }

    public void selectBrand() {
        newJobComponent.selectBrand(properties.getBrandName());
    }

    public void selectJobType(JobType jobType) {
        newJobComponent.selectJobType(jobType);
    }

    public void selectRegion() {
        regionsComponent.clickSave();
    }

    public void addInternalMessage(String topic, String internalNote) {
        internalMessageComponent.withTopicAndInternalNote(topic, internalNote);
    }

    public void selectDateAndTime() {
        dateAndTimeComponent.clickSave();
    }

    public void enterNameAndDescription(String jobName, String jobDescription) {
        nameAndDescriptionComponent.withJobNameAndDescription(jobName, jobDescription);
    }

    public void uploadFile(File file) {
        uploadFileComponent.uploadCsvFile(file);
    }

    public void navigateToJobList() {
        browserNavigation.navigateToUrl(properties.getBulkChangeManagerHomeUrl() + "jobs/list");
    }

    public boolean isJobListed(String jobName, String... possibleStatuses) {
        return jobsListComponent.isJobListed(jobName, possibleStatuses);
    }

    public String getJobId(String jobName) {
        return jobsListComponent.getJobId(jobName);
    }
    
    public void loginBackoffice() {
        browserNavigation.navigateToUrl(aliceBackofficeProperties.getUrlLogin());
        browserNavigation.navigateToUrl(aliceBackofficeProperties.getUrlHome());
    }
    
    public void navigateToBackofficeAccountInfo(String profileId) {
        browserNavigation.navigateToUrl(aliceBackofficeProperties.getUrlBase() + "/manager/profile/viewprofileinfo?PLAYER_KEY=" + profileId);
    }
    
    public boolean isBypassButtonDisplayed() {
    	return backofficeAccountInfoComponent.isBypassButtonDisplayed();
    }
    
    public void clickOnBypassButton() {
    	backofficeAccountInfoComponent.clickOnBypassButton();
    }
    
    public boolean isInternalMessageDisplayed(String internalNote) {
        return backofficeAccountInfoComponent.isInternalMessageDisplayed(internalNote);
    }
}
