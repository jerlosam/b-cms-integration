package io.crazy88.beatrix.e2e.clients;

import io.crazy88.beatrix.e2e.clients.dto.DepositCreationRequest;
import io.crazy88.beatrix.e2e.clients.dto.DepositCreationResponse;
import io.crazy88.beatrix.e2e.clients.dto.DepositResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@FeignClient(url = "${feign.cashier.url}", name = "deposits")
public interface DepositClient {

    @RequestMapping(method = PUT, value = "/deposits/{transactionId}")
    DepositCreationResponse bitcoinDeposit(@PathVariable("transactionId") String transactionId,
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "x-ecomm-origin") String ecommOrigin,
            @RequestBody DepositCreationRequest bitcoinDepositCreationRequest);

    @RequestMapping(method = GET, value = "/deposits/{depositId}")
    DepositResponse getDeposit(@PathVariable("depositId") String depositId,
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "x-ecomm-origin") String ecommOrigin);
}
