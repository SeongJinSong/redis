package spring.redis.talk;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class RoomMember {
    @Id @GeneratedValue
    Long id;
    String roomId;
    String userId;
    Long findCount;
}
