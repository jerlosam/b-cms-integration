package io.crazy88.beatrix.e2e.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import internal.katana.core.reporter.Reporter;
import internal.katana.selenium.BrowserManager;
import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.browser.navigation.BrowserNavigation;
import io.crazy88.beatrix.e2e.common.internal.components.InternalAppHeaderComponent;
import io.crazy88.beatrix.e2e.common.internal.components.InternalLoginComponent;

@Component
public class PortalActions {

    private static final String LOGIN_RELATIVE_PATH = "login?returnUrl=%2Fapps-list";

    @Autowired
    protected E2EProperties properties;

    @Autowired
    protected BrowserNavigation browserNavigation;

    @Autowired
    private BrowserManager browser;

    @Autowired
    protected InternalLoginComponent internalLogin;

    @Autowired
    private AppSelectorComponent appSelectorComponent;

    @Autowired
    private InternalAppHeaderComponent internalAppHeaderComponent;

    @Autowired
    private Reporter reporter;

    public void enterToPortalLoginPage() {
        browserNavigation.navigateToUrlWithRedirection(properties.getPortalHomeUrl(), LOGIN_RELATIVE_PATH);
    }
    public void enterToPortalHomePage() {
        enterToPortalLoginPage();
        internalLogin.as(properties.getQaLdapUser(), properties.getQaLdapPassword());
    }

    public boolean checkAppSelectorPresent() {
        return appSelectorComponent.isPresent();
    }

    public String getAppSelectorTextInApp() {
        internalAppHeaderComponent.toggleTabbedMenu();
        String text = appSelectorComponent.getText();
        internalAppHeaderComponent.toggleTabbedMenu();
        return text;
    }

    public void navigateToOneOfAvailableApps(){
        appSelectorComponent.navigateToFirstApp();
    }

    public boolean isOnPortalLoginPage(){
        String url = browser.driver().getCurrentUrl();
        reporter.info(String.format("Current URL: %s", url));
        return url.equals(properties.getPortalHomeUrl() + LOGIN_RELATIVE_PATH);
    }

}
