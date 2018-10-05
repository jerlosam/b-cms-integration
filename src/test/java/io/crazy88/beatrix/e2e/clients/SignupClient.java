package io.crazy88.beatrix.e2e.clients;

import io.crazy88.beatrix.e2e.player.dto.SignupRequest;
import io.crazy88.beatrix.e2e.player.dto.SignupResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(url = "${feign.signup.url}", name = "signup")
public interface SignupClient {

    @RequestMapping(method = RequestMethod.POST, value = "api/v1/signup")
    SignupResponse signup(@RequestBody SignupRequest signupRequest);

}
