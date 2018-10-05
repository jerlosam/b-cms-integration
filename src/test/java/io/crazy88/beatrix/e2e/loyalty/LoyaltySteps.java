package io.crazy88.beatrix.e2e.loyalty;

import static io.crazy88.beatrix.e2e.clients.LoyaltyProgramClient.extractProgramId;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.context.StepsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.clients.LoyaltyProgramClient;
import io.crazy88.beatrix.e2e.rewardmanager.actions.RewardManagerActions;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.actions.ProgramActions;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.components.LoyaltyHeaderComponent;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Program;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.factory.ProgramFactory;

@TestComponent
public class LoyaltySteps {

    private static final String PROGRAM_CONTEXT = "PROGRAM";

    private static final String PROGRAM_ID_CONTEXT = "PROGRAM_ID";

    @Autowired
    private BrowserManager browser;

    @Autowired
    private E2EProperties properties;

    @Autowired
    private LoyaltyHeaderComponent loyaltyHeaderComponent;

    @Autowired
    private LoyaltyProgramClient client;

    @Autowired
    private LoyaltyTestActions loyaltyTestActions;

    @Autowired
    private ProgramActions programActions;

    @Autowired
    private ProgramFactory programFactory;

    @Autowired
    private Reporter reporter;

    @Autowired
    private RewardManagerActions rewardManagerActions;

    private StepsContext stepsContext = new StepsContext();

    @Given("a user logged in Rewards Manager inside Loyalty section")
    public String userLoggedInRewardsManagerLoyaltySection() {
        rewardManagerActions.enterToRewardsManagerLoyaltySection();
        return browser.driver().getCurrentUrl();
    }

    @Given("an existing program in loyalty")
    @ToContext(PROGRAM_CONTEXT)
    public Program existingProgram() {
        Program program = programFactory.createProgramForEdition();
        ResponseEntity response = client.createProgram(program, properties.getQaLoyaltyLdapUser());

        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

        UUID programId = extractProgramId(response);
        stepsContext.put(PROGRAM_ID_CONTEXT, programId, ToContext.RetentionLevel.EXAMPLE);

        return program.withId(programId);
    }

    @When("Existing Programs tab is accessed")
    public void navigateToExistingProgramsTab() {
        loyaltyHeaderComponent.navigateToExistingPrograms();
    }

    @When("a Rewards Manager user creates a new program")
    @ToContext(PROGRAM_CONTEXT)
    public Program createsProgram() {
        final Program program = programFactory.createProgram();
        programActions.createProgram(program);
        return program;
    }

    @When("a RM user successfully edits the program")
    public void userEditsProgram(@FromContext(PROGRAM_CONTEXT) Program program) {
        programActions.editProgram(program);
    }

    @Then("loyalty header is displayed")
    public void loyaltyHeaderDisplays() {
        assertThat(loyaltyTestActions.isLoyaltyHeaderDisplayed()).isTrue();
    }

    @Then("call to loyalty-program-service is successful")
    public void isLoyaltyProgramCallSuccessful() {
        assertThat(loyaltyTestActions.isLoyaltyProgramCallSuccessful()).isTrue();
    }

    @Then("the program is displayed")
    public void thenProgramIsDisplayed(@FromContext(PROGRAM_CONTEXT) final Program program) {
        assertThat(loyaltyTestActions.isProgramDisplayed(program, false)).isTrue();
        final String programId = programActions.getProgramId(program);
        stepsContext.put(PROGRAM_ID_CONTEXT, programId, ToContext.RetentionLevel.EXAMPLE);
    }

    @Then("program is updated")
    public void thenTheProgramIsEdited(@FromContext(PROGRAM_CONTEXT) final Program program) {
        Assertions.assertThat(programActions.isProgramDisplayed(program, true)).isTrue();
    }

    @AfterScenario
    public void restoreProgram() {
        try {
            final String programId = String.valueOf(stepsContext.get(PROGRAM_ID_CONTEXT));
            reporter.info(String.format("Archive program [%s]", programId));
            client.archiveProgram(programId, properties.getQaLoyaltyLdapUser());
        } catch (final StepsContext.ObjectNotStoredException ignored) {
        }
    }

}
