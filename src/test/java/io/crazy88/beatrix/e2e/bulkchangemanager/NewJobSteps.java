package io.crazy88.beatrix.e2e.bulkchangemanager;

import static java.lang.String.format;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang.RandomStringUtils.randomAscii;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Profile;

import io.crazy88.beatrix.e2e.AliceBackofficeProperties;
import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.bulkchangemanager.actions.BulkChangeManagerActions;
import io.crazy88.beatrix.e2e.bulkchangemanager.actions.BulkChangeManagerSetupActions;
import io.crazy88.beatrix.e2e.bulkchangemanager.dto.BulkAccountEntry;
import io.crazy88.beatrix.e2e.bulkchangemanager.dto.JobInfo;
import io.crazy88.beatrix.e2e.bulkchangemanager.dto.JobType;
import io.crazy88.beatrix.e2e.clients.ProfileClient;
import io.crazy88.beatrix.e2e.clients.dto.ProfileId;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerSetupActions;
import io.crazy88.beatrix.e2e.player.JoinSteps;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile({ "local", "test" })
@TestComponent
public class NewJobSteps {
    
    private static final String CONTEXT_KEY_JOB_INFO = "jobInfo";

    public static final String CONTEXT_KEY_BULK_ACCOUNT_ENTRY = "bulkAccountEntry";

    @Autowired
    protected E2EProperties properties;

    @Autowired
    protected AliceBackofficeProperties aliceBackofficeProperties;

    @Autowired
    private BulkChangeManagerActions bulkChangeManagerActions;

    @Autowired
    private ProfileClient profileClient;

    @Autowired
    private BulkChangeManagerSetupActions bulkChangeManagerSetupActions;

    @Autowired
    private CustomerManagerSetupActions customerManagerSetupActions;

    @Given("bulk account entry data for the csv file")
    @ToContext(CONTEXT_KEY_BULK_ACCOUNT_ENTRY)
    public BulkAccountEntry givenBulkAccountEntry(@FromContext(JoinSteps.PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        // The wallet is created lazily
        String playerEmail = playerSignup.getEmail();
        customerManagerSetupActions.getBalances(playerEmail);
        AssertionsHelper.retryUntilSuccessful(5, () -> assertThat(profileClient.search(properties.getBrandCode(), playerEmail).size() > 0).isTrue());
        ProfileId profileId = profileClient.search(properties.getBrandCode(), playerEmail).get(0);
        return BulkAccountEntry.builder()
                .profileId(profileId.getProfileId())
                .categoryCode("FN-AD-XX")
                .externalDescription(randomAlphanumeric(20))
                .amount("25.50")
                .transactionRef(randomAlphanumeric(20)).build();
    }

    @When("the user creates a Force Phone update job")
    @ToContext(CONTEXT_KEY_JOB_INFO)
    public JobInfo whenTheUserCreatesForcePhoneUpdateJob() throws IOException {
        bulkChangeManagerActions.login();

        JobType jobType = JobType.FORCE_PHONE_UPDATE;
        bulkChangeManagerActions.selectJobType(jobType);
        log.info(format("The user select a job of type %s", jobType.name()));

        bulkChangeManagerActions.selectRegion();
        log.info("Region Selected");

        String internalMessage = randomInternalMessage();
        bulkChangeManagerActions.addInternalMessage("Accounts", internalMessage);
        Optional<String> optionalInternalMessage = Optional.of(internalMessage);
        log.info("Internal message added");

        bulkChangeManagerActions.selectDateAndTime();
        log.info("Job selected to be executed asap");

        String jobName = jobType.name() + "-" + System.currentTimeMillis();
        bulkChangeManagerActions.enterNameAndDescription(jobName, randomDescription());
        log.info("Job name and description entered");

        File csvFile = bulkChangeManagerSetupActions.generateForcePhoneUpdateCsvFile(aliceBackofficeProperties.getProfileId());
        bulkChangeManagerActions.uploadFile(csvFile);
        log.info("CSV file uploaded");

        return JobInfo.builder()
                .jobName(jobName)
                .internalMessage(optionalInternalMessage)
                .build();
    }

    @When("the user creates a Bulk Account Entry job")
    @ToContext(CONTEXT_KEY_JOB_INFO)
    public JobInfo whenTheUserCreatesBulkAccountEntryJob(@FromContext(CONTEXT_KEY_BULK_ACCOUNT_ENTRY) BulkAccountEntry bulkAccountEntry) throws IOException {
        bulkChangeManagerActions.login();

        bulkChangeManagerActions.selectBrand();

        JobType jobType = JobType.BULK_ACCOUNT_ENTRY;

        bulkChangeManagerActions.selectJobType(jobType);
        log.info(format("The user select a job of type %s", jobType.name()));


        bulkChangeManagerActions.selectDateAndTime();
        log.info("Job selected to be executed asap");

        String jobName = jobType.name() + "-" + System.currentTimeMillis();
        bulkChangeManagerActions.enterNameAndDescription(jobName, randomDescription());
        log.info("Job name and description entered");

        File csvFile = bulkChangeManagerSetupActions.generateBulkAccountEntryCsvFile(bulkAccountEntry);
        bulkChangeManagerActions.uploadFile(csvFile);
        log.info("CSV file uploaded");

        return JobInfo.builder()
                .jobName(jobName)
                .internalMessage(Optional.empty())
                .build();
    }
    
    @Then("pending action and internal message are displayed in Backoffice properly")
    public void thenNavigatesToBackofficeProfilePage(@FromContext(CONTEXT_KEY_JOB_INFO) JobInfo jobInfo) throws IOException {
    	String profileId = aliceBackofficeProperties.getProfileId();
    	bulkChangeManagerActions.loginBackoffice();
    	bulkChangeManagerActions.navigateToBackofficeAccountInfo(profileId);
    	
    	assertThat(bulkChangeManagerActions.isBypassButtonDisplayed()).isTrue();
    	log.info(format("Profile %s has a pending action", profileId));
    	assertThat(bulkChangeManagerActions.isInternalMessageDisplayed(jobInfo.getInternalMessage().get())).isTrue();
    	log.info("Expected Internal message displayed");
    	
    	bulkChangeManagerActions.clickOnBypassButton();
    	log.info(format("Pending action has been removed for profile %s", profileId));
    }

    private static String randomDescription() {
        return randomAscii(100);
    }
    
    private static String randomInternalMessage() {
    	return randomAlphanumeric(50);
    }
}
