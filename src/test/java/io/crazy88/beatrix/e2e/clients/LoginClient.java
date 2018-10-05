package io.crazy88.beatrix.e2e.clients;


import io.crazy88.beatrix.e2e.bonus.dto.LoginRequest;
import io.crazy88.beatrix.e2e.bonus.dto.LoginResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${feign.login.url}", name = "login")
public interface LoginClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/login")
    LoginResponse login(@RequestBody LoginRequest loginRequest);

}
