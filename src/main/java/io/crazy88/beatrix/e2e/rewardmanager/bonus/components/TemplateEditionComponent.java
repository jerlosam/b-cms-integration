package io.crazy88.beatrix.e2e.rewardmanager.bonus.components;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserForm;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.rewardmanager.dto.Template;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemplateEditionComponent {

    private static final By TEMPLATE_NAME_FLD = By.id("walletConstraintsTemplateForm-name");
    private static final By CASINO_CONTRIBUTION_FLD = By.id("walletConstraintsTemplateForm-casinoContribution");
    private static final By ADD_EXCEPTION_BUTTON = By.id("walletConstraintsTemplateForm-contribution-exception-1");

    private static final By DISCARD_BUTTON = By.cssSelector("button.discard");

    //EXCEPTIONS
    private static final By SAVE_BUTTON = By.id("bx-save");
    private static final By ALLOW_CHECKBOX = By.cssSelector("table td div.custom-checkbox");

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserForm browserForms;

    @Autowired
    private BrowserWait browserWaits;

    @Autowired
    private BrowserNavigation browserNavigations;

    public void createTemplate(Template template) {
        fillTemplate(template);
        addGameException();
        selectGameException();
        browserForms.submitForm();
    }

    public void fillTemplate(Template template) {
        browserForms.fillTextField(TEMPLATE_NAME_FLD, template.getTemplateName());
        browserForms.fillTextField(CASINO_CONTRIBUTION_FLD, Integer.toString(template.getCasinoContribution()));
        waitForButtonEnabled();
    }

    private void waitForButtonEnabled() {
        browserWaits.waitUntil((isButtonEnabled) -> browser.fluent().button(ADD_EXCEPTION_BUTTON).getAttribute("disabled") != null);
    }

    public void addGameException() {
        browserWaits.waitForClickableElement(ADD_EXCEPTION_BUTTON);
        browser.driver().findElement(ADD_EXCEPTION_BUTTON).click();
        browserWaits.waitForElement(ALLOW_CHECKBOX);
    }

    public void selectGameException() {
        browser.fluent().div(ALLOW_CHECKBOX).click();
        browser.fluent().button(SAVE_BUTTON).click();
    }

    public void discardTemplate() {
        browser.fluent().button(DISCARD_BUTTON).click();
    }

}
