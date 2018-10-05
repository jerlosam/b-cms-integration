package io.crazy88.beatrix.e2e.player.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.browser.BrowserValidation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;

@Component
public class MessagesComponent {

    public static final By MESSAGE_LIST_OVERLAY = By.cssSelector(".bx-messages-list");

    public static final By MESSAGE_LIST = By.cssSelector(".bx-messages-list ul.custom-menu.primary.check-list li");

    public static final By FIRST_MESSAGE =
            By.cssSelector(".bx-messages-list ul.custom-menu.primary.check-list li:nth-child(1) a");

    public static final By MESSAGE_DETAIL = By.cssSelector(".playerMessageDetail");

    public static final By MESSAGE_BUBBLE = By.cssSelector("span#message-number");

    public static final By MESSAGES_LIST_DATE = By.cssSelector(".bx-messages-list-date");

    public static final By MESSAGE_DETAIL_DATE = By.cssSelector(".bx-message-date");

    public static final By MESSAGE_DETAIL_BACK = By.cssSelector(".account-back-btn.bx-message-selected-btn");

    public static final By FIRST_MESSAGE_CHECK =
            By.cssSelector(".bx-messages-list ul.custom-menu.primary.check-list li:nth-child(1) label");

    public static final By DELETE_MESSAGES_BUTTON = By.cssSelector(".bx-messages-list #deleteButton");

    @Autowired
    private BrowserManager browser;

    @Autowired
    private BrowserWait browserWait;

    @Autowired
    private BrowserValidation browserValidation;

    public Boolean isMessageListDisplayed() {
        return browserValidation.isElementDisplayedWaitFor(MESSAGE_LIST, 10);
    }

    public Boolean isDisplayedMessageDetail() {
        return browserValidation.isElementDisplayedWaitFor(MESSAGE_DETAIL, 10);
    }

    public Boolean isDisplayedMessageBubble() {
        return browserValidation.isElementDisplayedWaitFor(MESSAGE_BUBBLE, 10);
    }

    public void openMessage() {
        browserWait.waitForAngular();
        browser.driver().findElement(FIRST_MESSAGE).click();
        browserWait.waitForAngular();
    }

    public void navigateBackToMessageList() {
        browser.driver().findElement(MESSAGE_DETAIL_BACK).click();
        browserWait.waitForAngular();
    }

    public void checksMessageFromTheList() {
        browserWait.waitForAngular();
        browser.driver().findElement(FIRST_MESSAGE_CHECK).click();
        browserWait.waitForAngular();
    }

    public void clickButtonToDeleteMessages() {
        browserWait.waitForAngular();
        browser.driver().findElement(DELETE_MESSAGES_BUTTON).click();
        browserWait.waitForAngular();
    }
}
