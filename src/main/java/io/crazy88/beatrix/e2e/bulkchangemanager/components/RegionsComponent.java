package io.crazy88.beatrix.e2e.bulkchangemanager.components;

import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import static org.openqa.selenium.By.cssSelector;

@Component
public class RegionsComponent extends WizardStageComponentBase {

    private static final By REGION = cssSelector("div.custom-radio.native-toggle.single-region");

    public void clickSave() {
        browserWaits.waitForElement(REGION);
        super.clickSave();
    }
}
