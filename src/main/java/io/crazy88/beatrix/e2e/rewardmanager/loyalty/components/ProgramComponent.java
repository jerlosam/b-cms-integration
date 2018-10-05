package io.crazy88.beatrix.e2e.rewardmanager.loyalty.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.BrowserForm;

@Component
public class ProgramComponent {

    private static final By NEW_LOYALTY_PROGRAM = By.className("bx-ljl-loyalty");

    private static final By NEW_LOYALTY_PROGRAM_BUTTON = By.cssSelector("nav.program-holder");

    @Autowired
    BrowserForm browserForm;

    public void createNewProgram() {
        browserForm.clickOnElement(NEW_LOYALTY_PROGRAM, NEW_LOYALTY_PROGRAM_BUTTON);
    }

}
