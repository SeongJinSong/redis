package spring.redis.talk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    Optional<RoomMember> findByRoomIdAndUserId(String roomId, String userId);
    RoomMember deleteRoomMemberByRoomIdAndUserId(String roomId, String userId);
    @Modifying
    @Query("update RoomMember rm set rm.findCount = rm.findCount+1 where rm.roomId= :#{#roomId} and rm.userId= :#{#userId}")
    void updateFindCount(@Param(value = "roomId") String roomId, @Param(value = "userId")String userId);
}
