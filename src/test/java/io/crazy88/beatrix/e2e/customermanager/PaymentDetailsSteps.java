package io.crazy88.beatrix.e2e.customermanager;

import io.crazy88.beatrix.e2e.bonus.dto.LoginRequest;
import io.crazy88.beatrix.e2e.bonus.dto.LoginResponse;
import io.crazy88.beatrix.e2e.clients.DepositClient;
import io.crazy88.beatrix.e2e.clients.LoginClient;
import io.crazy88.beatrix.e2e.clients.dto.DepositCreationResponse;
import io.crazy88.beatrix.e2e.clients.dto.DepositResponse;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;
import io.crazy88.beatrix.e2e.customermanager.dto.PaymentDetails;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import static io.crazy88.beatrix.e2e.cashier.DepositSteps.PAYMENT_CONTEXT;
import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

@TestComponent
public class PaymentDetailsSteps {

    public static final String INSTRUMENT_TYPE_DESCRIPTION = "Bitcoin";

    public static final String ECOMM_ORIGIN = "cashier30";

    @Autowired
    private DepositClient depositClient;

    @Autowired
    private LoginClient loginClient;

    @Autowired
    private CustomerManagerActions customerManagerActions;

    @Then("the payment details are displayed")
    public void paymentDetailsAreDisplayed(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup, @FromContext(PAYMENT_CONTEXT) DepositCreationResponse depositCreationResponse) {
        PaymentDetails paymentDetails = customerManagerActions.getDisplayedPaymentDetailsFromCustomerManager();
        LoginResponse loginResponse = doLogin(playerSignup);

        String authorization = "Bearer " + loginResponse.getSessionId();
        DepositResponse depositResponse = depositClient.getDeposit(depositCreationResponse.getId(), authorization, ECOMM_ORIGIN);

        assertThat(paymentDetails.getTitle()).contains(depositResponse.getId());
        assertThat(paymentDetails.getAccountNumber()).isEqualTo(depositResponse.getProfileId().toString());
        assertThat(paymentDetails.getInstrumentType()).isEqualTo(INSTRUMENT_TYPE_DESCRIPTION);

    }

    private LoginResponse doLogin(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        return loginClient.login(LoginRequest.builder()
                .username(playerSignup.getEmail())
                .password(playerSignup.getPassword())
                .build());
    }

}
