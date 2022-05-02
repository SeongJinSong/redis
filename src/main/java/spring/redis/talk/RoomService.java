package spring.redis.talk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final CacheRepository cacheRepository;
    private final RoomMemberRepository roomMemberRepository;

    void roomMemberFindCount(String roomId, String userId) {
        for(int i=0;i<10;i++){
            boolean validUser = cacheRepository.isValidUser(roomId, userId);
            if(validUser){
                log.info("##validUser roomId={}, userId={}", roomId, userId);
            }
        }
    }
}
