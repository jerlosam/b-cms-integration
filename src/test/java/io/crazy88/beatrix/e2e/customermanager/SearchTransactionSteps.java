package io.crazy88.beatrix.e2e.customermanager;

import io.crazy88.beatrix.e2e.clients.dto.DepositCreationResponse;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import static io.crazy88.beatrix.e2e.cashier.DepositSteps.PAYMENT_CONTEXT;

@TestComponent
public class SearchTransactionSteps {

    @Autowired
    private CustomerManagerActions customerManagerActions;

    @When("a customer manager searches a payment by id")
    public void userSearchsPaymentById(@FromContext(PAYMENT_CONTEXT) DepositCreationResponse depositCreationResponse) {
        customerManagerActions.searchByPaymentIdOnCustomerManager(depositCreationResponse.getId());
    }

}
