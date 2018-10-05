package io.crazy88.beatrix.e2e.clients;

import static java.util.Optional.ofNullable;

import java.net.URI;
import java.util.UUID;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriTemplate;

import io.crazy88.beatrix.e2e.rewardmanager.loyalty.dto.Program;

@FeignClient(url = "${feign.loyaltyProgram.url}", name = "loyaltyProgram")
public interface LoyaltyProgramClient {

    String PROGRAM_LOCATION_TEMPLATE = "/api/programs/{programId}";

    @RequestMapping(method = RequestMethod.POST, value = "/api/programs")
    ResponseEntity createProgram(Program program, @RequestHeader("X-OPERATOR") final String operator);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/programs/{programId}")
    ResponseEntity editProgram(Program program, @PathVariable("programId") String programId, @RequestHeader("X-OPERATOR") final String operator);

    @RequestMapping(method = RequestMethod.DELETE, value = "/api/programs/{programId}")
    ResponseEntity archiveProgram(@PathVariable("programId") String programId, @RequestHeader("X-OPERATOR") final String operator);

    static UUID extractProgramId(ResponseEntity entity) {

        UriTemplate uriTemplate = new UriTemplate(PROGRAM_LOCATION_TEMPLATE);

        return ofNullable(entity.getHeaders().getLocation())
                .map(URI::getPath)
                .map(location -> uriTemplate.match(location))
                .map(m -> m.get("programId"))
                .map(UUID::fromString)
                .orElse(null);
    }

}
