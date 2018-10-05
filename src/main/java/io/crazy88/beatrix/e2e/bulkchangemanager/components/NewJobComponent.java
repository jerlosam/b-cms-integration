package io.crazy88.beatrix.e2e.bulkchangemanager.components;

import static org.openqa.selenium.By.cssSelector;

import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.bulkchangemanager.dto.JobType;

@Component
public class NewJobComponent {
    
    private static final Map<JobType, By> JOBS_ELEMENTS = ImmutableMap.of(JobType.FORCE_PHONE_UPDATE, cssSelector(".bx-force-phone-update"),
            JobType.BULK_ACCOUNT_ENTRY, cssSelector(".bx-bulk-account-entry"));

    private static final By BRAND_SELECTOR = By.tagName("alice-brand-select");

    @Autowired
    private BrowserWait browserWaits;

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserForm browserForm;

    public void selectJobType(JobType jobType) {
        browserWaits.waitForElement(JOBS_ELEMENTS.get(jobType));
        browser.fluent().link(JOBS_ELEMENTS.get(jobType)).click();
    }

    public void selectBrand(String brand) {
        browserForm.selectValueOnSelector(BRAND_SELECTOR, brand);
    }
}
