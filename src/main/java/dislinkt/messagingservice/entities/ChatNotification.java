package dislinkt.messagingservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotification {

    private long id;
    private long senderId;
    private long recipientId;
    private long timestamp;


}
