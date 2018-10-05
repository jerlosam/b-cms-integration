package io.crazy88.beatrix.e2e.clients.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

@Data
@Wither
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagesById {

    private UUID messageId;

    private UUID profileId;

    private String topicCode;

    private boolean acknowledged;

    private boolean deleted;
    
    private String subject;

    private String content;

    private String internalContent;

    private MessageStatus status;

    private String createdBy;

    private Instant createdAt;

    private String updatedBy;

    private Instant updatedAt;

    private Instant publishedAt;

    @Getter(AccessLevel.NONE)
    private MessagePriority priority;

    private MessageType type;

    private boolean sendEmail;

    public MessagePriority getPriority() {
        return (priority == null) ? MessagePriority.NORMAL : priority;
    }
}
