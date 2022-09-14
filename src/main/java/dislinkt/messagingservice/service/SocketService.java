package dislinkt.messagingservice.service;


import com.corundumstudio.socketio.SocketIOClient;
import dislinkt.messagingservice.dto.ChatMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatNotificationService chatNotificationService;


    public void sendMessage(String room, SocketIOClient senderClient, ChatMessageDto messageDto) {
        messageDto = chatMessageService.processMessage(messageDto);
        boolean notFound = true;
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent("chat", messageDto);
                notFound = false;
                break;
            }
        }
        if (notFound) {
            chatNotificationService.publishNotification(messageDto);
        }
    }


}
