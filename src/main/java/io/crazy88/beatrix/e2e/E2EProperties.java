package io.crazy88.beatrix.e2e;

import io.crazy88.beatrix.e2e.player.clients.SiteConfigClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.util.List;

@Data
@ConfigurationProperties("e2e")
@Import({ EcommProperties.class, AliceBackofficeProperties.class })
public class E2EProperties {

    String remoteDriverUrl;
    String playerHomeUrl;
    String playerReferAFriendUrl;
    String playerContactUsUrl;
    List<String> referAFriendDisabled;
    Long referrerAliceAffiliate;

    String portalHomeUrl;
    String rewardManagerHomeUrl;
    String customerManagerHomeUrl;
    String bulkChangeManagerHomeUrl;

    String environment;
    String brandCode;
    String brandDomain;
    String brandName;
    String brandTemplate;
    String regionName;
    String countryCode;
    String currency;

    String qaLdapUser;
    String qaLdapPassword;

    String qaLoyaltyLdapUser;
    String qaLoyaltyLdapPassword;

    String player;

    String paymentId;

    @Autowired
    EcommProperties ecomm;

    @Autowired
    private SiteConfigClient siteConfigClient;

    public String getCurrency() {
        if(currency == null) {
            currency = siteConfigClient.getSignupConfig().getDefaultCurrency();
        }
        return currency;
    }

}
