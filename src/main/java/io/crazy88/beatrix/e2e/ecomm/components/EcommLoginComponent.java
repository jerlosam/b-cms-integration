package io.crazy88.beatrix.e2e.ecomm.components;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.BrowserForm;

@Component
public class EcommLoginComponent {

    private static final By USERNAME_FIELD = By.id("user-input");

    private static final By PASSWORD_FIELD = By.id("password-input");

    @Autowired
    private BrowserForm browserForms;

    public void as(String username, String password) {
        browserForms.fillTextField(USERNAME_FIELD, username);
        browserForms.fillTextField(PASSWORD_FIELD, password);
        browserForms.submitForm();
    }

}
