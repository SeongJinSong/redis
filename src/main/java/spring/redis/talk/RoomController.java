package spring.redis.talk;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    @GetMapping("roomId/{roomId}")
    void findRoomMember(@PathVariable String roomId, @RequestParam String userId){
        roomService.roomMemberFindCount(roomId, userId);
    }
}
