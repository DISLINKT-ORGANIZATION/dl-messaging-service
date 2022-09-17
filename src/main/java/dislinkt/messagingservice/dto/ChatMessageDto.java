package dislinkt.messagingservice.dto;

import dislinkt.messagingservice.entities.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private long id;
    private long senderId;
    private long recipientId;
    private long chatRoomId;
    private String content;
    private long timestamp;
    private boolean sendNotification;

    public ChatMessageDto(ChatMessage message) {
        this.id = message.getId();
        this.senderId = message.getSenderId();
        this.recipientId = message.getRecipientId();
        this.chatRoomId = message.getChatRoom().getId();
        this.content = message.getContent();
        this.timestamp = message.getTimestamp();
    }
}
