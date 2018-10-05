package io.crazy88.beatrix.e2e.bulkchangemanager.components;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

class WizardStageComponentBase {

    private static final By SAVE = cssSelector(".wizard-buttons > .right > button");

    private static final By DISCARD = id("bx-discard");

    @Autowired
    protected BrowserWait browserWaits;

    @Autowired
    protected BrowserManager browser;

    public void clickSave() {
        clickWizardButton(SAVE);
    }

    public void clickDiscard() {
        clickWizardButton(DISCARD);
    }

    private void clickWizardButton(By button) {
        browserWaits.waitForClickableElement(button);
        browser.fluent().button(button).click();
    }
}
