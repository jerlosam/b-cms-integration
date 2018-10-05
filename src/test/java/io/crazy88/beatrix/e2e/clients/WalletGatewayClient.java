package io.crazy88.beatrix.e2e.clients;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.crazy88.beatrix.e2e.transactions.dto.Transaction;
import io.crazy88.beatrix.e2e.transactions.dto.TransactionBatchCreated;

@FeignClient(url = "${feign.walletGateway.url}", name = "walletGateway")
public interface WalletGatewayClient {

    @RequestMapping(value = "services/wallet-gateway/v1/profiles/{profileId}/transactions", method = POST)
    TransactionBatchCreated createTransaction(@PathVariable("profileId") UUID profileId,
            @RequestBody List<Transaction> transactions);
}
