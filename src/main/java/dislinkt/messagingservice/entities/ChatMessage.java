package dislinkt.messagingservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "sender_id")
    private long senderId;

    @Column(name = "recepient_id")
    private long recipientId;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "message_status")
    private MessageStatus messageStatus;
}
