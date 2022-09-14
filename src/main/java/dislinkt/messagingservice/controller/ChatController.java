package dislinkt.messagingservice.controller;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import dislinkt.messagingservice.dto.ChatMessageDto;
import dislinkt.messagingservice.service.SocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final SocketIONamespace namespace;
    private final SocketService socketService;

    @Autowired
    public ChatController(SocketIOServer server, SocketService socketService) {
        this.namespace = server.addNamespace("/chat");
        this.socketService = socketService;
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("chat", ChatMessageDto.class, onChatReceived());
    }

    private DataListener<ChatMessageDto> onChatReceived() {
        return (senderClient, chatMessageDto, ackSender) -> {
            log.info(chatMessageDto.toString());
            socketService.sendMessage(chatMessageDto.getRecipientId() + "", senderClient, chatMessageDto);
        };
    }

    private ConnectListener onConnected() {
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
        };
    }

    private DisconnectListener onDisconnected() {
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.leaveRoom(room);
        };
    }

}
