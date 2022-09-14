package dislinkt.messagingservice.service;

import dislinkt.messagingservice.dto.ChatMessageDto;
import dislinkt.messagingservice.entities.ChatMessage;
import dislinkt.messagingservice.entities.ChatRoom;
import dislinkt.messagingservice.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository repository;

    @Autowired
    private ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        return repository.save(chatMessage);
    }

    public ChatMessageDto processMessage(ChatMessageDto chatMessageDto) {
        ChatRoom room = chatRoomService.getChatRoom(chatMessageDto.getSenderId(), chatMessageDto.getRecipientId());
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatRoom(room);
        Date now = new Date();
        chatMessage.setTimestamp(now.getTime());
        chatMessage.setSenderId(chatMessageDto.getSenderId());
        chatMessage.setRecipientId(chatMessageDto.getRecipientId());
        chatMessage.setContent(chatMessageDto.getContent());
        chatMessage = save(chatMessage);
        room.getMessages().add(chatMessage);
        chatRoomService.saveChatRoom(room);
        chatMessageDto.setChatRoomId(chatMessage.getChatRoom().getId());
        chatMessageDto.setId(chatMessage.getId());
        chatMessageDto.setTimestamp(chatMessage.getTimestamp());
        return chatMessageDto;
    }
}
