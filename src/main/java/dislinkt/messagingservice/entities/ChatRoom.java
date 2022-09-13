package dislinkt.messagingservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_1_id")
    private long user1Id;

    @Column(name = "user_2_id")
    private long user2Id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ChatMessage> messages = new ArrayList<>();

}
