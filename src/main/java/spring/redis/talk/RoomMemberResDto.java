package spring.redis.talk;

import lombok.Data;

@Data
public class RoomMemberResDto {
    String roomId;
    String userId;
    String count;
}
