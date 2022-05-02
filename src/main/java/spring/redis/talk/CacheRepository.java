package spring.redis.talk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import spring.redis.util.CacheKeyCreator;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CacheRepository {
    private final RoomMemberRepository roomMemberRepository;

    @Cacheable(
            value = CacheKeyCreator.ROOM_MEMBER,
            key = "T(spring.redis.util.CacheKeyCreator).createKey(#roomId, #userId)",
            unless = "#result == false"
    )
    public boolean isExistUser(String roomId, String userId){
        log.info("# existUserCheck");
        return roomMemberRepository.findByRoomIdAndUserId(roomId, userId).isPresent();
    }

    @Cacheable(
            value = CacheKeyCreator.ROOM_MEMBER,
            key = "T(spring.redis.util.CacheKeyCreator).createKey(#roomId, #userId)",
            unless = "#result == null"
    )
    public boolean updateRoomMember(String roomId, String userId){
        log.info("## updateRoomMember readId:{}, userId:{}", roomId, userId);
        Optional<RoomMember> roomMemberOptional = roomMemberRepository.findByRoomIdAndUserId(roomId, userId);
        roomMemberRepository.updateFindCount(roomId, userId);
        return roomMemberOptional.isPresent();
    }

    @Cacheable(
            value = CacheKeyCreator.ROOM_MEMBER,
            key = "T(spring.redis.util.CacheKeyCreator).createKey(#roomId, #userId)",
            unless = "#result == null"
    )
    public boolean createRoomMember(String roomId, String userId){
        log.info("## createRoomMember readId:{}, userId:{}", roomId, userId);
        //여기에 sleap 예제를 넣으면 될듯
        try{
            RoomMember savedRoomMember = roomMemberRepository.save(new RoomMember(roomId, userId, 0L));
            return true;
        }catch(Exception e){
            log.info(e.getMessage());
            throw e;
        }
    }

    @CacheEvict(
            value = CacheKeyCreator.ROOM_MEMBER,
            key = "T(spring.redis.util.CacheKeyCreator).createKey(#roomId, #userId)"
    )
    public void deleteRoomUser(String roomId, String userId){
        log.info("## deleteRoomMember readId:{}, userId:{}", roomId, userId);
        RoomMember roomMember = roomMemberRepository.deleteRoomMemberByRoomIdAndUserId(roomId, userId);
        log.info("## deletedMemberId:{}", roomMember.userId);
    }
}
