package dislinkt.messagingservice.repository;

import dislinkt.messagingservice.entities.ChatMessage;
import dislinkt.messagingservice.entities.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    long countBySenderIdAndRecipientIdAndMessageStatus(long senderId, long recipientId, MessageStatus status);
    List<ChatMessage> findByChatId(String chatId);
    List<ChatMessage> findBySenderIdAndRecipientId(long senderId, long recipientId);

}
