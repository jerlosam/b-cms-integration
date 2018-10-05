package io.crazy88.beatrix.e2e.cms.actions;

import io.crazy88.beatrix.e2e.cms.components.CmsComponents;
import io.crazy88.beatrix.e2e.player.actions.PlayerNavigationActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.crazy88.beatrix.e2e.E2EProperties;

@Component
public class CmsActions {

    @Autowired
    private E2EProperties properties;

    @Autowired
    private CmsComponents cmsComponents;


    public void enterCMSSite() {
        cmsComponents.navigateToCMS();
        cmsComponents.loginToCMS(properties.getQaLdapUser(), properties.getQaLdapPassword());
      }

    public void createContentForSlotsLV(){
        cmsComponents.selectBrand("Slots LV");
        cmsComponents.createContent();
    }

    public void createPromotionsContent(){
        cmsComponents.createContentPage();
    }

    public void createPromotionsMenu(){
        cmsComponents.createContent();
        cmsComponents.createMenu();
    }

    public void createPromotionsLandingPage(){
        cmsComponents.createContent();
        cmsComponents.createMarketingContent();
    }
}
