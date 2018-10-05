package io.crazy88.beatrix.e2e.clients;

import io.crazy88.beatrix.e2e.clients.dto.Referral;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(url = "${feign.referral.url}", name = "referral-service")
public interface ReferralClient {

    @RequestMapping(method = RequestMethod.GET, value = "/services/referral/v1/profiles/{profileId}")
    Referral getProfile(@PathVariable("profileId") UUID profileId);

    @RequestMapping(method = RequestMethod.PUT, value = "/services/referral/v1/profiles/{profileId}/?externalProfileId={externalProfileId}&referredByType=AFFILIATE")
    Referral addRefereeAffiliateWithExternalProfileId(@PathVariable("profileId") UUID profileId, @PathVariable("externalProfileId") Long externalProfileId, @RequestHeader(value = "X-BRAND") String brandCode);

}