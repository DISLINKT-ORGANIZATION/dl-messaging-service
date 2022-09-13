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

    @Column(name = "sender_id")
    private long senderId;

    @Column(name = "recepient_id")
    private long recipientId;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    private long timestamp;

    @ManyToOne
    private ChatRoom chatRoom;

}
