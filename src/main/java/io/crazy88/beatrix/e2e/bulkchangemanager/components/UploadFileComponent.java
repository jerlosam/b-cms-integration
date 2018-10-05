package io.crazy88.beatrix.e2e.bulkchangemanager.components;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;

import java.io.File;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class UploadFileComponent extends WizardStageComponentBase {

    private static final By FILE = id("fileUpload");

    private static final By UPLOAD_SUCCESS = className("upload-success");

    @Autowired
    protected BrowserManager browser;

    @Autowired
    private BrowserWait browserWait;

    public void uploadCsvFile(File file) {
        String url = browser.driver().getCurrentUrl();
        browser.fluent().input(FILE).sendKeys(file.getAbsolutePath());
        browserWait.waitForElement(UPLOAD_SUCCESS);
        clickSave();
        browserWait.waitForUrlChange(url);
        browserWait.waitForPageToBeLoadedCompletely();
    }
}
