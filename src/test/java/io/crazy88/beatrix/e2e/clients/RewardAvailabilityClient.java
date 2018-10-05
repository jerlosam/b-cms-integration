package io.crazy88.beatrix.e2e.clients;


import io.crazy88.beatrix.e2e.clients.dto.AvailableRewards;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${feign.rewardAvailability.url}", name = "rewardAvailability")
public interface RewardAvailabilityClient {

    @RequestMapping(method = RequestMethod.GET, value = "/services/reward-availability/v1/profiles/{profileId}/available-rewards")
    AvailableRewards getAvailableRewards(@PathVariable String profileId);
}
