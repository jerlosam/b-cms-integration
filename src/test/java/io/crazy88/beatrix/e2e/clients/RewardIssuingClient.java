package io.crazy88.beatrix.e2e.clients;


import io.crazy88.beatrix.e2e.bonus.dto.IssueRewardRequest;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(url = "${feign.rewardIssuing.url}", name = "rewardIssuing")
public interface RewardIssuingClient {

    @RequestMapping(method = RequestMethod.POST,
            value = "services/reward-issuing/v1/profiles/{profileId}/claimed-rewards")
    void issueBonus(@RequestHeader("Authorization") String authorization,
                    @PathVariable UUID profileId, @RequestBody IssueRewardRequest rewardRequest);
}
