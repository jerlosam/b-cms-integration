package io.crazy88.beatrix.e2e.player.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.browser.wait.BrowserWait;
import io.crazy88.beatrix.e2e.player.components.ReactiveChatComponent;

@Component
public class ReactiveChatActions {

    @Autowired
    protected BrowserNavigation browserNavigation;

    @Autowired
    protected BrowserWait browserWait;

    @Autowired
    private ReactiveChatComponent reactiveChat;

    public void clickOnChatNowLink() {
        reactiveChat.clickOnChatNowLink();
    }

    public boolean isReactiveChatDisplayed() {
        return reactiveChat.isReactiveChatDisplayed();
    }

}
