package io.crazy88.beatrix.e2e.customermanager;

import static org.assertj.core.api.Assertions.assertThat;

import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;

@TestComponent
public class ActivitySteps {

    @Autowired
    CustomerManagerActions customerManagerActions;

    @Then("an activity with $action action is displayed on the activities list")
    public void thenAnActivityWithActionIsDisplayed(String action){
        AssertionsHelper.retryUntilSuccessful( () -> {
            boolean isDisplayed = customerManagerActions.isActivityWithActionDisplayed(action);
            assertThat(isDisplayed).isTrue();
        });
    }
}
