package io.crazy88.beatrix.e2e.clients;

import io.crazy88.beatrix.e2e.clients.dto.Template;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.crazy88.beatrix.boot.service.controller.Slice;

@FeignClient(url = "${feign.walletConstraints.url}", name = "walletConstraints")
public interface WalletConstraintsClient {

    @RequestMapping(method = RequestMethod.GET,
            value = "services/wallet-constraints/v1/brands/{brandCode}/templates")
    Slice<Template> getTemplates(@PathVariable("brandCode") String brandCode);
}
