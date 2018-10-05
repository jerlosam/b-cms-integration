package io.crazy88.beatrix.e2e.clients;

import io.crazy88.beatrix.boot.client.feign.location.LocationHeader;
import io.crazy88.beatrix.e2e.bonus.dto.CreateRewardRequest;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(url = "${feign.rewardManagement.url}", name = "rewardManagement")
public interface RewardManagementClient {

    @RequestMapping(method = RequestMethod.POST, value = "services/reward-management/v1/rewards")
    @LocationHeader(name = "rewardId",
            uri = "http://{reward-management-service}/services/reward-management/v1/rewards/{rewardId}")
    UUID createReward(@RequestBody CreateRewardRequest createRewardRequest);
}
