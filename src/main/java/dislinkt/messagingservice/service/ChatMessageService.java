package dislinkt.messagingservice.service;

import dislinkt.messagingservice.entities.ChatMessage;
import dislinkt.messagingservice.entities.MessageStatus;
import dislinkt.messagingservice.exception.ResourceNotFoundException;
import dislinkt.messagingservice.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository repository;

    @Autowired
    private ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setMessageStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(long senderId, long recipientId) {
        return repository.countBySenderIdAndRecipientIdAndMessageStatus(senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(long senderId, long recipientId) {
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);

        var messages =
                chatId.map(cId -> repository.findByChatId(cId)).orElse(new ArrayList<>());

        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    public ChatMessage findById(long id) {
        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setMessageStatus(MessageStatus.DELIVERED);
                    return repository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("can't find message (" + id + ")"));
    }

    public void updateStatuses(long senderId, long recipientId, MessageStatus status) {
        List<ChatMessage> messages = repository.findBySenderIdAndRecipientId(senderId, recipientId);
        for (ChatMessage chatMessage: messages) {
            chatMessage.setMessageStatus(status);
            repository.save(chatMessage);
        }
    }




}
