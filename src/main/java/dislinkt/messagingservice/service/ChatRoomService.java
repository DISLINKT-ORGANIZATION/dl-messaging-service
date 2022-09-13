package dislinkt.messagingservice.service;

import dislinkt.messagingservice.dto.ChatMessageDto;
import dislinkt.messagingservice.dto.ChatRoomDto;
import dislinkt.messagingservice.entities.ChatRoom;
import dislinkt.messagingservice.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public ChatRoom getChatRoom(long senderId, long recipientId) {
        ChatRoom room = chatRoomRepository.findOneByUser1IdAndUser2Id(senderId, recipientId);
        if (!Objects.isNull(room)) {
            return room;
        }
        ChatRoom roomReverse = chatRoomRepository.findOneByUser1IdAndUser2Id(recipientId, senderId);
        if (!Objects.isNull(roomReverse)) {
            return roomReverse;
        }
        room = new ChatRoom();
        room.setUser1Id(senderId);
        room.setUser2Id(recipientId);
        room = chatRoomRepository.save(room);
        return room;
    }

    public void saveChatRoom(ChatRoom room) {
        chatRoomRepository.save(room);
    }

    public ChatRoomDto getAllMessages(long user1Id, long user2Id) {
        ChatRoom chatRoom = getChatRoom(user1Id, user2Id);
        ChatRoomDto dto = new ChatRoomDto();
        dto.setChatRoomId(chatRoom.getId());
        dto.setMessages(chatRoom.getMessages().stream().map(ChatMessageDto::new).collect(Collectors.toList()));
        return dto;
    }
}
