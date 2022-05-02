package spring.redis.talk;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class RoomMember {
    @Id @GeneratedValue
    Long id;
    String roomId;
    String userId;
    Long findCount;

    public RoomMember(String roomId, String userId, Long findCount) {
        this.roomId = roomId;
        this.userId = userId;
        this.findCount = findCount;
    }


}
