package io.crazy88.beatrix.e2e.player.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import io.crazy88.beatrix.e2e.E2EProperties;
import io.crazy88.beatrix.e2e.clients.MessageClient;
import io.crazy88.beatrix.e2e.clients.ProfileClient;
import io.crazy88.beatrix.e2e.clients.dto.MessagePriority;
import io.crazy88.beatrix.e2e.clients.dto.MessageStatus;
import io.crazy88.beatrix.e2e.clients.dto.MessageType;
import io.crazy88.beatrix.e2e.clients.dto.MessagesById;
import io.crazy88.beatrix.e2e.clients.dto.ProfileId;

@TestComponent
public class MessageActions {

    @Autowired
    private E2EProperties properties;

    @Autowired
    private MessageClient messageClient;

    @Autowired
    private ProfileClient profileClient;

    public String createAMessage(String email) {
        ProfileId profileId = profileClient.search(properties.getBrandCode(), email).get(0);
        MessagesById messagesById = MessagesById.builder()
                                        .content("Test content")
                                        .createdBy("e2e test operator")
                                        .priority(MessagePriority.NORMAL)
                                        .profileId(profileId.getProfileId())
                                        .status(MessageStatus.FINAL)
                                        .subject("e2e subject")
                                        .topicCode("account")
                                        .type(MessageType.PLAYER)
                                        .build();
        MessagesById message = messageClient.create(profileId.getProfileId(), messagesById);
        return message.getMessageId().toString();
    }
}
