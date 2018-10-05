package io.crazy88.beatrix.e2e.bulkchangemanager.components;

import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.openqa.selenium.By.xpath;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobsListComponent {

    private static final String STATUS_CELL_JOB_WITH_NAME = "//tbody[@id='bulkManagementList']/tr/td[contains(@class,'name-col')]/a/span[text()='%s']/../../../td[contains(@class,'status-col')]/span";

    private static final String TABLE_RAW_JOB_WITH_NAME = "//tbody[@id='bulkManagementList']/tr/td[contains(@class,'name-col')]/a/span[text()='%s']/../../..";

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserWait browserWait;

    public boolean isJobListed(String jobName, String... possibleStatuses) {
        By jobStatusCell = xpath(String.format(STATUS_CELL_JOB_WITH_NAME, jobName));
        browserWait.waitForElement(jobStatusCell);
        String status = browser.driver().findElement(jobStatusCell).getText();
        log.info("Job status found is {}", status);
        return contains(possibleStatuses, status);
    }

    public String getJobId(String jobName) {
        By jobRaw = xpath(String.format(TABLE_RAW_JOB_WITH_NAME, jobName));
        browserWait.waitForElement(jobRaw);
        String jobId = browser.driver().findElement(jobRaw).getAttribute("id").replace("job-", "");
        return jobId;
    }
}
