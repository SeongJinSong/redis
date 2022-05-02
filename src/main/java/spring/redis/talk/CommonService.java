package spring.redis.talk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import spring.redis.util.CacheKeyCreator;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {
    private final RoomMemberRepository roomMemberRepository;

    @Cacheable(
        value = CacheKeyCreator.ROOM_MEMBER,
        key = "T(ai.wapl.talk.util.CacheKeyCreator).createKey(#req.talkId, #req.userId)"
    )
    public boolean isValidUser(String roomId, String userId){
        log.info("check");
        return roomMemberRepository.findByRoomIdAAndUserId(roomId, userId).isPresent();
    }
}
