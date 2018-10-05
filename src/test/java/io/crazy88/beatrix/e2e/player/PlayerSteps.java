package io.crazy88.beatrix.e2e.player;

import static io.crazy88.beatrix.e2e.player.JoinSteps.PLAYER_SIGNUP_CONTEXT;
import static org.assertj.core.api.Assertions.assertThat;

import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.FromContext;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.player.actions.MessageActions;
import io.crazy88.beatrix.e2e.player.actions.PlayerAccountMenuActions;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import io.crazy88.beatrix.e2e.player.actions.ResetPasswordActions;
import io.crazy88.beatrix.e2e.player.components.ForgotPasswordComponent;
import io.crazy88.beatrix.e2e.player.components.HeaderComponent;
import io.crazy88.beatrix.e2e.player.components.LoginComponent;
import io.crazy88.beatrix.e2e.player.components.MessagesComponent;
import io.crazy88.beatrix.e2e.player.components.NotificationsComponent;
import io.crazy88.beatrix.e2e.player.components.ResetPasswordComponent;
import io.crazy88.beatrix.e2e.player.dto.PlayerSignup;

@TestComponent
public class PlayerSteps {

    private static final String TEST_RESET_PASSWORD = "Testing2";

    @Autowired
    E2EProperties e2eProperties;

    @Autowired
    private PlayerNavigationActions playerNavigationActions;

    @Autowired
    private PlayerAccountMenuActions playerAccountMenuActions;

    @Autowired
    private HeaderComponent headerComponent;

    @Autowired
    private LoginComponent loginComponent;

    @Autowired
    private ForgotPasswordComponent forgotPasswordComponent;

    @Autowired
    private NotificationsComponent notificationsComponent;

    @Autowired
    private ResetPasswordActions resetPasswordActions;

    @Autowired
    private MessageActions messageActions;

    @Autowired
    private ResetPasswordComponent resetPasswordComponent;

    @Autowired
    private MessagesComponent messagesComponent;

    @Autowired
    private BrowserNavigation browserNavigation;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private E2EProperties properties;


    @When("a player request to reset his password")
    public void whenPlayerRequestResetPassword(@FromContext(PLAYER_SIGNUP_CONTEXT)PlayerSignup player) {
        playerNavigationActions.enterSite();
        playerNavigationActions.navigateToLogin();
        loginComponent.navigateToForgotPassword();
        forgotPasswordComponent.requestResetPasswordLink(player.getEmail());
    }


    @When("the player navigates to home page")
    public void navigateToHomePage(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        playerNavigationActions.enterSite();
        playerNavigationActions.logIn(playerSignup.getEmail(), playerSignup.getPassword());
    }

    @When("the player navigates to messages page")
    public void navigateToMessagesPage() {
        headerComponent.navigateToAccountMenu();
        playerAccountMenuActions.navigateToMessages();
    }

    @When("has messages")
    @Alias("has unread messages")
    public void hasMessages(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        messageActions.createAMessage(playerSignup.getEmail());
    }

    @When("player opens a message")
    public void openAMessage(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        navigateToHomePage(playerSignup);
        navigateToMessagesPage();
        messagesComponent.isMessageListDisplayed();
        messagesComponent.openMessage();
    }

    @When("the player checks a message")
    public void playerChecksAMessage(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        navigateToHomePage(playerSignup);
        navigateToMessagesPage();
        messagesComponent.isMessageListDisplayed();
        messagesComponent.checksMessageFromTheList();
    }

    @When("clicks the button to delete selected")
    public void clicksTheButtonToDeleteSelectedMessages(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        messagesComponent.clickButtonToDeleteMessages();
    }

    @Then("the player can see the list of messages")
    public void thePlayerCanSeeTheListOfMessages(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        navigateToHomePage(playerSignup);
        navigateToMessagesPage();
        assertThat(messagesComponent.isMessageListDisplayed()).isTrue();
    }

    @Then("the message is deleted")
    public void theMessageIsDeleted() {
        notificationsComponent.waitForNotificationsNotDisplayed();
        assertThat(messagesComponent.isMessageListDisplayed()).isFalse();
    }

    @Then("the messages bubble is displayed")
    public void theMessagesBubbleIsDisplayed(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        navigateToHomePage(playerSignup);
        assertThat(messagesComponent.isDisplayedMessageBubble()).isTrue();
        navigateToMessagesPage();
        assertThat(messagesComponent.isDisplayedMessageBubble()).isTrue();

    }

    @Then("the player can see the message details")
    public void thePlayerCanSeeTheMessageDetails(@FromContext(PLAYER_SIGNUP_CONTEXT) PlayerSignup playerSignup) {
        assertThat(messagesComponent.isDisplayedMessageDetail()).isTrue();
    }

    @Then("a reset password link is sent")
    public void thenEmailWithPasswordResetLinkSent() {
        notificationsComponent.waitForNotification();
        assertThat(notificationsComponent.isSuccessNotificationDisplayed()).isTrue();
    }


    @When("a player reset his password")
    public void whenPlayerResetPassword(@FromContext(PLAYER_SIGNUP_CONTEXT)PlayerSignup player) {

        playerNavigationActions.enterSite();

        String resetPasswordUrl = resetPasswordActions.getResetPasswordUrl(player.getEmail());
        browserNavigation.navigateToUrl(properties.getPlayerHomeUrl() + resetPasswordUrl);

        resetPasswordComponent.resetPassword(TEST_RESET_PASSWORD);
    }


    @Then("the player can log in with his new password")
    public void thenPlayerLogsInWithNewPassword(@FromContext(PLAYER_SIGNUP_CONTEXT)PlayerSignup player) {

        loginComponent.as(player.getEmail(), TEST_RESET_PASSWORD);
        browserWait.waitForUrl(e2eProperties.getPlayerHomeUrl());

        assertThat(playerNavigationActions.isLoggedIn()).isTrue();
    }
}
