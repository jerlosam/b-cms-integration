package io.crazy88.beatrix.e2e.clients;

import io.crazy88.beatrix.e2e.clients.dto.ProfileId;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(url = "${feign.profile.url}", name = "profile")
public interface ProfileClient {

    @RequestMapping(value = "api/v1/profiles/search", method = GET)
    List<ProfileId> search(@RequestParam("brandCode") String brandCode, @RequestParam("query") String query);

}
