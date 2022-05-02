package spring.redis.talk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import spring.redis.util.CacheKeyCreator;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CacheRepository {
    private final RoomMemberRepository roomMemberRepository;

    @Cacheable(
            value = CacheKeyCreator.ROOM_MEMBER,
            key = "T(spring.redis.util.CacheKeyCreator).createKey(#roomId, #userId)"
    )
    public boolean isValidUser(String roomId, String userId){
        log.info("check");
        return roomMemberRepository.findByRoomIdAndUserId(roomId, userId).isPresent();
    }

    @CacheEvict(
            value = CacheKeyCreator.ROOM_MEMBER,
            key = "T(spring.redis.util.CacheKeyCreator).createKey(#roomId, #userId)"
    )
    public void deleteRoomUser(String roomId, String userId){
        log.info((String) CacheKeyCreator.createKey(roomId, userId));
        RoomMember roomMember = roomMemberRepository.deleteRoomMemberByRoomIdAndUserId(roomId, userId);
        log.info("## deletedMemberId:{}", roomMember.userId);

    }
}
