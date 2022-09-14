package dislinkt.messagingservice.service;

import dislinkt.messagingservice.dto.ChatMessageDto;
import dislinkt.messagingservice.entities.ChatNotification;
import dislinkt.messagingservice.kafka.KafkaNotification;
import dislinkt.messagingservice.kafka.KafkaNotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatNotificationService {

    @Autowired
    private KafkaTemplate<String, KafkaNotification> kafkaTemplate;

    public void publishNotification(ChatMessageDto messageDto) {
        ChatNotification notification = new ChatNotification();
        notification.setSenderId(messageDto.getSenderId());
        notification.setRecipientId(messageDto.getRecipientId());
        notification.setTimestamp(messageDto.getTimestamp());
        KafkaNotification kafkaNotification = new KafkaNotification(notification, KafkaNotificationType.MESSAGE_SENT);
        kafkaTemplate.send("dislinkt-user-notifications", kafkaNotification);
    }
}
