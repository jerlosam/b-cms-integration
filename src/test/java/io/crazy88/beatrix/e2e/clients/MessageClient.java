package io.crazy88.beatrix.e2e.clients;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.UUID;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.crazy88.beatrix.e2e.clients.dto.MessagesById;

@FeignClient(url = "${feign.messages.url}", name = "messages")
public interface MessageClient {

    @RequestMapping(value = "/services/messages/v1/profiles/{profileId}/messages", method = POST)
    MessagesById create(@PathVariable("profileId") UUID profileId, @RequestBody MessagesById messagesById);

}
