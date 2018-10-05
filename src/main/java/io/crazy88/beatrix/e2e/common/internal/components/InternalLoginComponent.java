package io.crazy88.beatrix.e2e.common.internal.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.BrowserForm;

@Component
public class InternalLoginComponent {

    private static final By USERNAME_FIELD = By.id("internalLogin-username");

    private static final By PASSWORD_FIELD = By.id("internalLogin-password");

    @Autowired
    private BrowserForm browserForms;
    
    public void as(String player, String password) {
        browserForms.fillTextField(USERNAME_FIELD,player);
        browserForms.fillTextField(PASSWORD_FIELD,password);
        browserForms.submitForm();
    }

}
