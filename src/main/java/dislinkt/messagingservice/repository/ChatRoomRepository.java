package dislinkt.messagingservice.repository;

import dislinkt.messagingservice.entities.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    ChatRoom findOneByUser1IdAndUser2Id(long user1Id, long user2Id);

}
