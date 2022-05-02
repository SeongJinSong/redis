package spring.redis.talk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
    private final CacheRepository cacheRepository;
    private final RoomMemberRepository roomMemberRepository;

    void roomMemberFindCount(String roomId, String userId) {
        for(int i=0;i<10;i++){
            boolean validUser = cacheRepository.isValidUser(roomId, userId);
            if(validUser){
                log.info("##validUser roomId={}, userId={}", roomId, userId);
                cacheRepository.updateRoomMember(roomId, userId);
            }
        }
    }

    public void createRoomMember(String roomId, String userId) {
        boolean validUser = cacheRepository.isValidUser(roomId, userId);
        if(validUser){
            log.info("##exist RoomMember roomId={}, userId={}", roomId, userId);
            cacheRepository.createRoomMember(roomId, userId);
        }
    }
}
