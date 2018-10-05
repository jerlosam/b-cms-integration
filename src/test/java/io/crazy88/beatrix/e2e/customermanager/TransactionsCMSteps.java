package io.crazy88.beatrix.e2e.customermanager;

import static io.crazy88.beatrix.e2e.customermanager.AccountDetailsSteps.MANUAL_ACCOUNT_ENTRY_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.bulkchangemanager.NewJobSteps;
import io.crazy88.beatrix.e2e.bulkchangemanager.dto.BulkAccountEntry;
import io.crazy88.beatrix.e2e.customermanager.actions.CustomerManagerActions;
import io.crazy88.beatrix.e2e.customermanager.dto.ManualAccountEntry;

@TestComponent
public class TransactionsCMSteps {

    @Autowired
    CustomerManagerActions customerManagerActions;

    @Then("a transaction with the manual account entry reference is displayed on the transactions list")
    public void thenATransactionWithRefIsDisplayed(@FromContext(MANUAL_ACCOUNT_ENTRY_CONTEXT) ManualAccountEntry manualAccountEntry){
        thenATransactionWithRefIsDisplayed(manualAccountEntry.getTransactionRef());
    }

    @Then("a transaction with the bulk account entry reference is displayed on the transactions list")
    public void thenATransactionWithRefIsDisplayed(@FromContext(NewJobSteps.CONTEXT_KEY_BULK_ACCOUNT_ENTRY) BulkAccountEntry bulkAccountEntry) {
        thenATransactionWithRefIsDisplayed(bulkAccountEntry.getTransactionRef());
    }

    private void thenATransactionWithRefIsDisplayed(String transactionRef) {
        AssertionsHelper.retryUntilSuccessful( () -> {
            boolean isDisplayed = customerManagerActions.isTransactionWithReferenceDisplayed(transactionRef);
            assertThat(isDisplayed).isTrue();
        });
    }
}
