package io.crazy88.beatrix.e2e.rewardmanager.loyalty.components;

import static io.crazy88.beatrix.e2e.rewardmanager.loyalty.factory.ProgramFactory.EDITION_ADD_TEXT;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Program;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistingProgramsComponent {

    public static final By CONTENT = By.className("bx-navigation");

    private static final By EDIT_PROGRAM_BUTTON = By.cssSelector("button.primary");

    //This element is only present if call to Loyalty Program fails
    private static final By ERROR = By.cssSelector("figure.custom-notification alert error");

    private static final By EXISTING_PROGRAMS = By.className("bx-ljl-existing-programs");

    @Autowired
    private BrowserManager browserManager;

    @Autowired
    private BrowserNavigation browserNavigations;

    @Autowired
    private BrowserWait browserWait;

    public String getProgramId(final Program program) {
        browserWait.waitForElement(EXISTING_PROGRAMS);
        return browserManager.fluent().elements(By.className("bx-ljl-program-details")).stream()
                .filter(e -> e.element(By.tagName("h2")).getWebElement().getText().startsWith(program.getNames().get("en")))
                .map(e -> e.getWebElement().getAttribute("id").replace("program-", ""))
                .findFirst().orElse(null);
    }

    public boolean isLoyaltyProgramCallSuccessful() {
        try {
            browserWait.waitForElement(ERROR);
            return false;
        } catch (final TimeoutException e) {
            return true;
        }
    }

    public boolean isProgramDisplayed(final Program program, boolean afterEdition) {
        browserWait.waitForElement(EXISTING_PROGRAMS);
        final String name = afterEdition ? program.getNames().get("en") + EDITION_ADD_TEXT : program.getNames().get("en");
        final By programSelector = By.xpath(String.format("//h2[contains(text(), '%s')]", name));
        return browserManager.fluent().elements(programSelector).stream().findAny().isPresent();
    }

    public void editProgram(String programId) {
        final By program = By.id(String.format("program-%s", programId));
        browserWait.waitForElement(program);
        browserManager.fluent().element(program).element(EDIT_PROGRAM_BUTTON).click();
    }

}
