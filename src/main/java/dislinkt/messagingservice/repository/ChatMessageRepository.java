package dislinkt.messagingservice.repository;

import dislinkt.messagingservice.entities.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    ChatMessage findById(long id);
    List<ChatMessage> findBySenderIdAndRecipientId(long senderId, long recipientId);

}
