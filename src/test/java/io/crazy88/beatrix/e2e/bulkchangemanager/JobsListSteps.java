package io.crazy88.beatrix.e2e.bulkchangemanager;

import static io.crazy88.beatrix.e2e.AssertionsHelper.retryUntilSuccessful;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Profile;

import io.crazy88.beatrix.e2e.bulkchangemanager.actions.BulkChangeManagerActions;
import io.crazy88.beatrix.e2e.bulkchangemanager.dto.JobInfo;
import io.crazy88.beatrix.e2e.clients.BulkChangeClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile({ "local", "test" })
@TestComponent
public class JobsListSteps {

    private static final String JOB_STATUS_CREATED = "Created";

    private static final String JOB_STATUS_IN_PROGRESS = "In Progress";

    private static final String JOB_STATUS_COMPLETED = "Completed";
    
    private static final String CONTEXT_KEY_JOB_INFO = "jobInfo";

    private static final int MAX_WAIT_MILLIS = 31000;

    private static final int WAIT_MARGIN_MILLIS = 5000;

    private static final long WAIT_INTERVAL_MILLIS = 1000;

    @Autowired
    private BulkChangeManagerActions bulkChangeManagerActions;

    @Autowired
    private BulkChangeClient bulkChangeClient;
    
    @Then("the job is successfully executed and completed")
    public void thenTheJobIsSuccessfullyExecutedAndCompleted(@FromContext(CONTEXT_KEY_JOB_INFO) JobInfo jobInfo) {
        assertJobIsInStatus(jobInfo.getJobName(), JOB_STATUS_CREATED, JOB_STATUS_IN_PROGRESS, JOB_STATUS_COMPLETED);
        String jobId = bulkChangeManagerActions.getJobId(jobInfo.getJobName());
        log.info(format("Job with jobId %s has been created", jobId));
        
        retryUntilSuccessful(MAX_WAIT_MILLIS + WAIT_MARGIN_MILLIS, WAIT_INTERVAL_MILLIS, () -> {
            assertJobIsInStatus(jobInfo.getJobName(), JOB_STATUS_COMPLETED);
        });
        log.info(format("Job with jobId %s has been completed", jobId));
        
        bulkChangeClient.deleteJob(jobId);
        log.info(format("Job with jobId %s has been removed", jobId));
    }

    private void assertJobIsInStatus(String jobName, String... possibleStatuses) {
        assertThat(bulkChangeManagerActions.isJobListed(jobName, possibleStatuses)).isTrue();
    }
}
