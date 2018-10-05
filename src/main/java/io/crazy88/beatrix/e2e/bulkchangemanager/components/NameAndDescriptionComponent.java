package io.crazy88.beatrix.e2e.bulkchangemanager.components;

import static org.openqa.selenium.By.id;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.BrowserForm;

@Component
public class NameAndDescriptionComponent extends WizardStageComponentBase {

    private static final By JOB_NAME = id("jobNames-jobName");

    private static final By JOB_DESCRIPTION = id("jobNames-jobDescription");

    @Autowired
    protected BrowserForm browserForm;

    public void withJobNameAndDescription(String jobName, String description) {
        browserForm.fillTextField(JOB_NAME, jobName);
        browserForm.fillTextAreaField(JOB_DESCRIPTION, description);
        clickSave();
    }
}
