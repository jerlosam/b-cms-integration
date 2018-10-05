package io.crazy88.beatrix.e2e.common.internal.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InternalAppHeaderComponent {

    @Autowired
    private TabbedMenuComponent tabbedMenuComponent;

    @Autowired
    private UserProfileComponent userProfileComponent;

    public void toggleTabbedMenu(){
        tabbedMenuComponent.toggle();
    }

    public void logout(){
        userProfileComponent.logout();
    }

}
