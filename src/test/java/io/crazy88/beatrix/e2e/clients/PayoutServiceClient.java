package io.crazy88.beatrix.e2e.clients;

import io.crazy88.beatrix.e2e.payoutservice.dto.PayoutRequest;
import io.crazy88.beatrix.e2e.payoutservice.dto.PayoutServiceRequest;
import io.crazy88.beatrix.e2e.payoutservice.dto.PayoutServiceResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(url = "${feign.payoutService.url}payout-requests", name = "payoutservice")
public interface PayoutServiceClient {

    @RequestMapping(method = RequestMethod.POST)
    PayoutRequest requestPayout(@RequestHeader(value = "x-ecomm-channel") String channelCode,
                                @RequestBody PayoutRequest payoutRequest);

}
