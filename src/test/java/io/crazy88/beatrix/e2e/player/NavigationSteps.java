package io.crazy88.beatrix.e2e.player;

import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class NavigationSteps {

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @When("a player navigates to login overlay")
    public void whenPlayerNavigatesToLogin(){
        playerNavigationActions.enterSite();
        playerNavigationActions.navigateToLogin();
    }

    @When("a player navigates to signup overlay")
    public void whenPlayerNavigatesToSignup(){
        playerNavigationActions.enterSite();
        playerNavigationActions.navigateToJoin();
    }

}
