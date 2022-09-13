package dislinkt.messagingservice.service;


import com.corundumstudio.socketio.SocketIOClient;
import dislinkt.messagingservice.controller.ChatController;
import dislinkt.messagingservice.dto.ChatMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SocketService {

    private static final Logger log = LoggerFactory.getLogger(SocketService.class);

    @Autowired
    private ChatMessageService chatMessageService;

    public void sendMessage(String room, SocketIOClient senderClient, ChatMessageDto messageDto) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                messageDto = chatMessageService.processMessage(messageDto);
                client.sendEvent("chat", messageDto);
            }
        }
    }
}
