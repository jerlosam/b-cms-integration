package io.crazy88.beatrix.e2e.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(url = "${feign.brandProperties.url}", name = "brandProperties")
public interface BrandPropertiesClient {
    @RequestMapping(method = RequestMethod.GET, value = "services/brand-admin/v1/brands/{brandCode}/properties?regions={regionId}")
    Map<String, List<String>> getPropertiesPerRegion(@PathVariable("brandCode") String brandCode,
            @PathVariable("regionId") String region);

    @RequestMapping(method = RequestMethod.GET, value = "services/brand-admin/v1/brands/{brandCode}/properties/{prefix}")
    Map<String, List<String>> getProperties(@PathVariable("brandCode") String brandCode,
            @PathVariable("prefix") String prefix);
}
