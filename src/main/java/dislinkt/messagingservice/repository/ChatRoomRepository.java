package dislinkt.messagingservice.repository;

import dislinkt.messagingservice.entities.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findBySenderIdAndRecipientId(long senderId, long recipientId);

}
