package io.crazy88.beatrix.e2e.cashier;

import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.cashier.actions.DepositActions;
import io.crazy88.beatrix.e2e.clients.dto.DepositCreationResponse;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.ToContext;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;

@Profile("production")
@Component
public class DepositProductionSteps extends DepositSteps{

    @Autowired
    private DepositActions depositActions;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Autowired
    private E2EProperties properties;

    @When("the player create a pinCode")
    public void createPinCode(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
       //As we use a hardcoded player for production we don't have to create a pin code
    }

    @Given("the existing player has made a deposit")
    @ToContext(PAYMENT_CONTEXT)
    public DepositCreationResponse playerMadeADeposit(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        //As we use a hardcoded payment for production we don't need to create a new deposit
        return DepositCreationResponse.builder().id(properties.getPaymentId()).build();
    }

    @When("the player navigates to deposit page")
    public void navigateToDepositPage(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        playerNavigationActions.enterSite();
        playerNavigationActions.logIn(playerSignup.getEmail(), playerSignup.getPassword());
        //our hardcoded player doesn't need to check if pin code is needed or not
        depositActions.navigateToDepositPage();
    }
}