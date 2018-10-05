package io.crazy88.beatrix.e2e.common.internal.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.crazy88.beatrix.e2e.common.internal.components.InternalAppHeaderComponent;
import io.crazy88.beatrix.e2e.portal.AppSelectorComponent;

@Component
public class InternalAppActions {

    @Autowired
    private InternalAppHeaderComponent internalAppHeaderComponent;

    @Autowired
    private AppSelectorComponent appSelectorComponent;

    public void switchToApp(String appText) {
        internalAppHeaderComponent.toggleTabbedMenu();
        appSelectorComponent.navigateToApp(appText);
    }

    public void logout() {
        internalAppHeaderComponent.logout();
    }

}
