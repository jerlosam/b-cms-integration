package io.crazy88.beatrix.e2e.clients;

import io.crazy88.beatrix.e2e.clients.dto.ProfileId;
import io.crazy88.beatrix.e2e.clients.dto.Token;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(url = "${feign.temporaryToken.url}", name = "temporaryToken")
public interface TemporaryTokenClient {

    @RequestMapping(value = "services/temporary-token/v1/token", method = POST)
    Token createToken(ProfileId profileId);
}
