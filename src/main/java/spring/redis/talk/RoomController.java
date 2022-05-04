package spring.redis.talk;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.redis.util.AccessLimit;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    @GetMapping("roomId/{roomId}")
    void findRoomMember(@PathVariable String roomId, @RequestParam String userId){
        roomService.roomMemberFindCount(roomId, userId);
    }
    @AccessLimit
    @PostMapping("roomId/{roomId}/userId/{userId}")
    void createRoomMember(@PathVariable String roomId, @PathVariable String userId){
        roomService.createRoomMember(roomId, userId);
    }
}
