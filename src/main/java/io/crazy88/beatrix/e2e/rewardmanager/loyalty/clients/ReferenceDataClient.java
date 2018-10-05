package io.crazy88.beatrix.e2e.rewardmanager.loyalty.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.crazy88.beatrix.e2e.rewardmanager.loyalty.clients.dto.BrandRegion;
import io.crazy88.beatrix.e2e.rewardmanager.loyalty.clients.dto.Slice;

@FeignClient(url = "${feign.referenceData.url}", name = "referenceData")
public interface ReferenceDataClient {

    @RequestMapping(method = RequestMethod.GET, value = "services/reference-data/v1/brands/{brandCode}/regions")
    Slice<BrandRegion> readAllByBrand(@PathVariable("brandCode") String brandCode);
}
