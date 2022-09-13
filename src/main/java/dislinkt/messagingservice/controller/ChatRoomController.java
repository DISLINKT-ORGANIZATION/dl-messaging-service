package dislinkt.messagingservice.controller;

import dislinkt.messagingservice.dto.ChatRoomDto;
import dislinkt.messagingservice.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/chat-room")
public class ChatRoomController {

    private final ChatRoomService service;

    @Autowired
    public ChatRoomController(ChatRoomService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{user1Id}/{user2Id}")
    public ResponseEntity<Object> getAllMessages(@PathVariable long user1Id, @PathVariable long user2Id) {
        ChatRoomDto dto = service.getAllMessages(user1Id, user2Id);
        return ResponseEntity.ok(dto);
    }


}
