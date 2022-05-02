package spring.redis.talk;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RoomMember {
    @Id @GeneratedValue
    Long id;
    String roomId;
    String userId;
    Long count;
}
