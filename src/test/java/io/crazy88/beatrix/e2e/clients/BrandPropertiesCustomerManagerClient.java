package io.crazy88.beatrix.e2e.clients;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${feign.brandPropertiesCustomerManager.url}", name = "brandProperties")
public interface BrandPropertiesCustomerManagerClient {

    @RequestMapping(method = RequestMethod.GET, value = "v1/brands/{brandCode}/properties/{prefix}")
    Map<String, List<String>> getProperties(@RequestHeader(AUTHORIZATION) String auth,
                                            @PathVariable("brandCode") String brandCode,
                                            @PathVariable("prefix") String prefix);

}
