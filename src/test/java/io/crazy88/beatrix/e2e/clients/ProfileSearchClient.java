package io.crazy88.beatrix.e2e.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.crazy88.beatrix.e2e.clients.dto.ProfileSearchResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(url = "${feign.profileSearch.url}", name = "profileSearch")
public interface ProfileSearchClient {

    @JsonProperty("items")
    @RequestMapping(value = "v1/profiles", method = GET)
    ProfileSearchResponse searchProfile(@RequestHeader(AUTHORIZATION) String auth,
                                        @RequestParam("free_text") String searchCriteria);

}
