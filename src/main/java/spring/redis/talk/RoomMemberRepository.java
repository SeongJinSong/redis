package spring.redis.talk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    Optional<RoomMember> findByRoomIdAndUserId(String roomId, String userId);
    RoomMember deleteRoomMemberByRoomIdAndUserId(String roomId, String userId);
//    @Modifying
//    @Query("update RoomMember rm set rn. where rm.roomId")
}
