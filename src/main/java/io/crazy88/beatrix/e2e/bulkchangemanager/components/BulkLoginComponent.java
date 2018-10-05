package io.crazy88.beatrix.e2e.bulkchangemanager.components;

import static org.openqa.selenium.By.id;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.browser.BrowserForm;

@Component
public class BulkLoginComponent {

    private static final By USERNAME = id("internalLogin-username");

    private static final By PASSWORD = id("internalLogin-password");

    @Autowired
    private BrowserForm browserForms;

    public void login(String username, String password) {
        browserForms.fillTextField(USERNAME, username);
        browserForms.fillTextField(PASSWORD, password);
        browserForms.submitForm();
    }
}
