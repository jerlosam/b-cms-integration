package io.crazy88.beatrix.e2e.reactivechat;

import static org.assertj.core.api.Assertions.assertThat;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.AssertionsHelper;
import io.crazy88.beatrix.e2e.cashier.actions.DepositActions;
import io.crazy88.beatrix.e2e.player.actions.ContactUsActions;
import io.crazy88.beatrix.e2e.player.actions.ReactiveChatActions;
import io.crazy88.beatrix.e2e.tools.ReactiveChatEnable;

@Component
public class ReactiveChatSteps {

    @Autowired
    private ReactiveChatActions reactiveChatActions;

    @Autowired
    private ContactUsActions contactUs;

    @Autowired
    private DepositActions depositActions;

    @ReactiveChatEnable
    @Given("the player clicks on the Chat Now link in Contact Us")
    @When("the player clicks on the Chat Now link in Contact Us")
    public void playerClicksOnChatNowLinkInContactUs() {

        // temporary fix for the problem caused by KIDDO-4204 (forcing focus loss)
        contactUs.clickOnHeader();

        reactiveChatActions.clickOnChatNowLink();
    }

    @ReactiveChatEnable
    @Given("the player clicks on the Chat Now link")
    @When("the player clicks on the Chat Now link")
    public void playerClicksOnChatNowLink() {
        reactiveChatActions.clickOnChatNowLink();
    }

    @ReactiveChatEnable
    @Given("the reactive chat is displayed")
    @When("the reactive chat is displayed")
    @Then("the reactive chat is displayed")
    public void reactiveChatIsDisplayed() {
        AssertionsHelper.retryUntilSuccessful(15,
                () -> assertThat(reactiveChatActions.isReactiveChatDisplayed()).isTrue());
    }

    @ReactiveChatEnable
    @When("the player can see the deposit payment method selection page")
    public void thenThePlayersCanSeeTheDepositPaymentMethodSelectionPage() {
        AssertionsHelper.retryUntilSuccessful(15, () -> {
            assertThat(depositActions.isDepositDisplayed()).isTrue();
        });
    }

}