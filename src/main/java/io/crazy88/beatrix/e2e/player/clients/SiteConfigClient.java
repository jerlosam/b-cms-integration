package io.crazy88.beatrix.e2e.player.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${feign.siteConfig.url}", name = "siteConfig")
public interface SiteConfigClient {

    @RequestMapping(method = RequestMethod.GET, value = "v1/signupform")
    SignupConfigResponse getSignupConfig();

    @RequestMapping(method = RequestMethod.GET, value = "v1/countries/{countryCode}/profileupdateform")
    ProfileUpdateConfigResponse getProfileUpdateConfig(@PathVariable("countryCode") String countryCode);
}
